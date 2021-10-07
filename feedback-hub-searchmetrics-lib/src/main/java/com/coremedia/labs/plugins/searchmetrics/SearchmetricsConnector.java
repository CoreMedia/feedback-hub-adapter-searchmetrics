package com.coremedia.labs.plugins.searchmetrics;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.httpurlconnection.HttpUrlConnectionExecutor;
import org.dmfs.oauth2.client.BasicOAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.BasicOAuth2Client;
import org.dmfs.oauth2.client.BasicOAuth2ClientCredentials;
import org.dmfs.oauth2.client.OAuth2AccessToken;
import org.dmfs.oauth2.client.OAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.OAuth2Client;
import org.dmfs.oauth2.client.OAuth2ClientCredentials;
import org.dmfs.oauth2.client.OAuth2InteractiveGrant;
import org.dmfs.oauth2.client.grants.AuthorizationCodeGrant;
import org.dmfs.oauth2.client.scope.BasicScope;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.uris.LazyUri;
import org.dmfs.rfc5545.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

/**
 *
 */
public class SearchmetricsConnector {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsConnector.class);
  private static final String ENDPOINT = "https://api.searchmetrics.com/some/endpoint";
  private static final String AUTHORIZE_URL = "https://login.searchmetrics.com/authorize";
  private static final String TOKEN_URL = "https://login.searchmetrics.com/oauth/token";
  private static final String SCOPES = "create:briefs read:briefs update:briefs";

  private RestTemplate restTemplate;
  private AuthorizationToken authorizationToken;


  public SearchmetricsConnector(@Qualifier("searchmetricsRestTemplate") RestTemplate searchmetricsRestTemplate) {
    this.restTemplate = searchmetricsRestTemplate;
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
    if (post.isPresent()) {
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
    if (post.isPresent()) {
      T responseBody = post.get().getBody();
      return Optional.of(responseBody);
    }
    return Optional.empty();
  }

  private void refreshToken(@NonNull SearchmetricsSettings settings) {
    try {
      // Create HttpRequestExecutor to execute HTTP requests
      // Any other HttpRequestExecutor implementation will do
      HttpRequestExecutor executor = new HttpUrlConnectionExecutor();

      // Create OAuth2 provider
      OAuth2AuthorizationProvider provider = new BasicOAuth2AuthorizationProvider(
              URI.create(AUTHORIZE_URL),
              URI.create(TOKEN_URL),
              new Duration(1, 0, 3600) /* default expiration time in case the server doesn't return any */);

      // Create OAuth2 client credentials
      OAuth2ClientCredentials credentials = new BasicOAuth2ClientCredentials(
              settings.getClientId(), settings.getClientSecret());

      // Create OAuth2 client
      OAuth2Client client = new BasicOAuth2Client(
              provider,
              credentials,
              new LazyUri(new Precoded(settings.getRedirectUrl())) /* Redirect URL */);

      // Start an interactive Authorization Code Grant
      OAuth2InteractiveGrant grant = new AuthorizationCodeGrant(
              client, new BasicScope(SCOPES.split(" ")));

      // Get the authorization URL and open it in a WebView
      URI authorizationUrl = grant.authorizationUrl();

      // Open the URL in a WebView and wait for the redirect to the redirect URL
      // After the redirect, feed the URL to the grant to retrieve the access token
      Uri uri = new LazyUri(new Precoded(settings.getRedirectUrl()));
      OAuth2AccessToken oAuth2AccessToken = grant.withRedirect(uri).accessToken(executor);

      System.out.println(oAuth2AccessToken.accessToken());
    }
    catch (Exception e) {
      LOG.error("Failed to refresh oauth token: " + e.getMessage(), e);
    }
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
      return (new Date().getTime() - creationDate.getTime() + (this.expiresIn * 1000)) > 0;
    }
  }
}
