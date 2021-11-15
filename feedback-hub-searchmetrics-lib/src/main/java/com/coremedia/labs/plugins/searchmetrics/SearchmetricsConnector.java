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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class SearchmetricsConnector {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsConnector.class);
  private static final String ENDPOINT = "https://api.searchmetrics.com/v4/";
  private static final String SCOPES = "grant_type=client_credentials";

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

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(payload, buildHttpHeaders());
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
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      headers.set("Accept-Charset", "utf-8");
      headers.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((settings.getApiKey() + ":" + settings.getApiSecret()).getBytes()));

      Map<String, String> map= new HashMap<>();
      map.put("grant_type", "client_credentials");

      HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(map, headers);
      Optional<ResponseEntity<AuthorizationToken>> token = post(entity, "token", AuthorizationToken.class);
      if (token.isEmpty()) {
        LOG.error("Failed to retrieve access token, check log for details.");
      } else {
        this.authorizationToken = token.get().getBody();
      }
    } catch (Exception e) {
      LOG.error("Failed to refresh oauth token: " + e.getMessage(), e);
    }
  }

  @NonNull
  private HttpHeaders buildHttpHeaders() {
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

  /**
   * {"access_token":"2d1d7a1b25c7f3d1ce10f0649da697fa10db87ed","expires_in":3600,"token_type":"Bearer","scope":null}
   */
  static class AuthorizationToken {

    @JsonProperty("access_token")
    private String accessToken;

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
