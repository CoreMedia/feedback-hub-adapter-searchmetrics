package com.coremedia.labs.plugins.searchmetrics;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public class SearchmetricsConnector {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsConnector.class);
  private static final String ENDPOINT = "https://graphql.searchmetrics.com";
  private static final String REGISTRATION_ID = "coremedia";
  private static final String TOKEN_URL = "https://login.searchmetrics.com/oauth/token";
  private static final String SCOPE = "create:briefs%20read:briefs%20update:briefs";
  private static final String GRANT_TYPE = "authorization_code";

  private RestTemplate restTemplate;
  private AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;
  private AuthorizationToken authorizationToken;
  private ClientRegistration clientRegistration;


  public SearchmetricsConnector(@Qualifier("searchmetricsRestTemplate") RestTemplate searchmetricsRestTemplate,
                                AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager) {
    this.restTemplate = searchmetricsRestTemplate;
    this.authorizedClientServiceAndManager = authorizedClientServiceAndManager;
  }

  @NonNull
  public <T> Optional<T> postResource(@NonNull SearchmetricsSettings settings,
                                      @Nullable String contextPath,
                                      @NonNull String payload,
                                      @NonNull Class<T> responseType) {
    if (authorizationToken == null || authorizationToken.isExpired()) {
      refreshToken(settings);
    }

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(payload, buildHttpHeaders(settings));
    Optional<ResponseEntity<T>> post = post(entity, contextPath, responseType);
    if(post.isPresent()) {
      T responseBody = post.get().getBody();
      return Optional.of(responseBody);
    }
    return Optional.empty();
  }

  @NonNull
  public <T> Optional<T> getResource(@NonNull SearchmetricsSettings settings,
                                      @Nullable String contextPath,
                                      @NonNull Class<T> responseType) {
    if (authorizationToken == null || authorizationToken.isExpired()) {
      refreshToken(settings);
    }

    Optional<ResponseEntity<T>> post = get(contextPath, responseType);
    if(post.isPresent()) {
      T responseBody = post.get().getBody();
      return Optional.of(responseBody);
    }
    return Optional.empty();
  }

  private void refreshToken(@NonNull SearchmetricsSettings settings) {
    if (clientRegistration == null) {
      String redirectUrl = settings.getRedirectUrl();
      if (!redirectUrl.endsWith("/")) {
        redirectUrl += "/";
      }

      clientRegistration = ClientRegistration
              .withRegistrationId(REGISTRATION_ID)
              .tokenUri(TOKEN_URL)
              .clientId(settings.getClientId())
              .clientSecret(settings.getClientSecret())
              .scope(SCOPE)
              .redirectUri(redirectUrl)
              .authorizationGrantType(new AuthorizationGrantType(GRANT_TYPE))
              .build();
    }


    OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(REGISTRATION_ID)
            .principal("Searchmetrics API")
            .build();

    // Perform the actual authorization request using the authorized client service and authorized client
    // manager. This is where the JWT is retrieved from the Okta servers.
    OAuth2AuthorizedClient authorizedClient = this.authorizedClientServiceAndManager.authorize(authorizeRequest);

    // Get the token from the authorized client object
    OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();

    LOG.info("Issued: " + accessToken.getIssuedAt().toString() + ", Expires:" + accessToken.getExpiresAt().toString());
    LOG.info("Scopes: " + accessToken.getScopes().toString());
    LOG.info("Token: " + accessToken.getTokenValue());

    Map<String,String> data = new HashMap<>();
    data.put("client_id", settings.getClientId());
    data.put("client_secret", settings.getClientSecret());
    data.put("grant_type", "refresh_token");
    data.put("code", accessToken.getTokenValue());
    data.put("redirect_uri", settings.getRedirectUrl());

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/x-www-form-urlencoded");
    headers.set("Accept-Charset", "utf-8");

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(data, headers);
    Optional<ResponseEntity<AuthorizationToken>> post = post(entity, "oauth/token", AuthorizationToken.class);
    post.ifPresent(authorizationTokenResponseEntity -> this.authorizationToken = authorizationTokenResponseEntity.getBody());
  }

  @NonNull
  private HttpHeaders buildHttpHeaders(@NonNull SearchmetricsSettings settings) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    headers.set("Accept-Charset", "utf-8");
    headers.set("Authorization", this.authorizationToken.accessToken);
    return headers;
  }


  @NonNull
  private <T> Optional<ResponseEntity<T>> get(@NonNull String contextPath,
                                              @NonNull Class<T> responseType) {
    ResponseEntity<T> responseEntity = restTemplate.getForEntity(ENDPOINT + contextPath, responseType);
    return Optional.of(responseEntity);
  }

  @NonNull
  private <T> Optional<ResponseEntity<T>> post(@NonNull HttpEntity entity,
                                               @NonNull String contextPath,
                                               @NonNull Class<T> responseType) {
    ResponseEntity<T> responseEntity = restTemplate.postForEntity(ENDPOINT + contextPath, entity, responseType);
    return Optional.of(responseEntity);
  }

  static class AuthorizationToken {

    @JsonProperty("access_token")
    private String accessToken;
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    private Date creationDate = new Date();

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }

    public String getScope() {
      return scope;
    }

    public void setScope(String scope) {
      this.scope = scope;
    }

    public String getTokenType() {
      return tokenType;
    }

    public void setTokenType(String tokenType) {
      this.tokenType = tokenType;
    }

    public int getExpiresIn() {
      return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
      this.expiresIn = expiresIn;
    }

    boolean isExpired() {
      return (new Date().getTime() - creationDate.getTime()+(this.expiresIn*1000)) > 0;
    }
  }
}
