package com.coremedia.blueprint.searchmetrics;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

/**
 *
 */
public class SearchmetricsConnector {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsConnector.class);
  private static final String ENDPOINT = "https://graphql.searchmetrics.com";
  private RestTemplate restTemplate;

  public SearchmetricsConnector(@Qualifier("searchmetricsRestTemplate") RestTemplate searchmetricsRestTemplate) {
    this.restTemplate = searchmetricsRestTemplate;
  }

  @NonNull
  public <T> Optional<T> postResource(@NonNull SearchmetricsSettings settings,
                                      @Nullable String payload,
                                      @NonNull Class<T> responseType) {
    HttpEntity<String> httpEntity = buildRequestEntity(settings, payload);
    return performRequest(HttpMethod.POST, httpEntity, responseType);
  }

  @NonNull
  private <T> Optional<T> performRequest(@NonNull HttpMethod httpMethod,
                                         @NonNull HttpEntity<String> requestEntity,
                                         @NonNull Class<T> responseType) {
    Optional<ResponseEntity<T>> responseEntityOptional = makeExchange(httpMethod, requestEntity, responseType);
    if (!responseEntityOptional.isPresent()) {
      return Optional.empty();
    }
    ResponseEntity<T> responseEntity = responseEntityOptional.get();
    T responseBody = responseEntity.getBody();
    return Optional.ofNullable(responseBody);
  }

  @NonNull
  private HttpEntity<String> buildRequestEntity(@NonNull SearchmetricsSettings settings, @Nullable String payload) {
    return new HttpEntity<>(payload.replaceAll("\n", " "), buildHttpHeaders(settings));
  }

  @NonNull
  private HttpHeaders buildHttpHeaders(@NonNull SearchmetricsSettings settings) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    headers.set("Accept", "application/json");
    headers.set("Accept-Charset", "utf-8");
    headers.set("sm-api-key", settings.getApiKey());
    headers.set("sm-api-secret", settings.getSharedSecret());
    return headers;
  }

  @NonNull
  private <T> Optional<ResponseEntity<T>> makeExchange(@NonNull HttpMethod httpMethod,
                                                       @NonNull HttpEntity<String> requestEntity,
                                                       @NonNull Class<T> responseType) {
    ResponseEntity<T> responseEntity = restTemplate.exchange(URI.create(ENDPOINT), httpMethod, requestEntity, responseType);
    return Optional.of(responseEntity);
  }
}
