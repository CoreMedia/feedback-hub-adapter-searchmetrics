package com.coremedia.labs.plugins.searchmetrics;

import com.coremedia.labs.plugins.searchmetrics.helper.BriefingAssigments;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 *
 */
@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
public class SearchmetricsServiceConfiguration {

  @Bean
  public BriefingAssigments briefingAssigments() {
    return new BriefingAssigments();
  }

  @Bean
  public SearchmetricsService searchMetricsService(CapConnection capConnection, SearchmetricsConnector searchmetricsConnector, BriefingAssigments briefingAssigments) {
    return new SearchmetricsServiceImpl(capConnection.getCache(), searchmetricsConnector, briefingAssigments);
  }

  @Bean
  public SearchmetricsConnector searchmetricsConnector(RestTemplate searchmetricsRestTemplate,
                                                       AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager) {
    return new SearchmetricsConnector(searchmetricsRestTemplate, authorizedClientServiceAndManager);
  }


  // Create the authorized client manager and service manager using the
  // beans created and configured above
  @Bean
  public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager (
          ClientRegistrationRepository clientRegistrationRepository,
          OAuth2AuthorizedClientService authorizedClientService) {

    OAuth2AuthorizedClientProvider authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                    .clientCredentials()
                    .build();

    AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
            new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                    clientRegistrationRepository, authorizedClientService);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }

  @Bean
  public RestTemplate searchmetricsRestTemplate() {
    // configure date (de)serialization
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // configure template
    RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    messageConverter.setPrettyPrint(true);
    messageConverter.setObjectMapper(objectMapper);
    restTemplate.getMessageConverters().removeIf(m -> m.getClass().isAssignableFrom(MappingJackson2HttpMessageConverter.class));
    restTemplate.getMessageConverters().add(messageConverter);
    restTemplate.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));
    return restTemplate;
  }
}
