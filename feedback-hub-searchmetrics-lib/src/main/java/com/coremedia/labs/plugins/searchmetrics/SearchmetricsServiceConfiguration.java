package com.coremedia.labs.plugins.searchmetrics;

import com.coremedia.cap.common.CapConnection;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.labs.plugins.searchmetrics.helper.BriefingAssigments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
  public SearchmetricsConnector searchmetricsConnector(RestTemplate searchmetricsRestTemplate) {
    return new SearchmetricsConnector(searchmetricsRestTemplate);
  }

  @Bean
  public RestTemplate searchmetricsRestTemplate() {
    RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    restTemplate.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));
    return restTemplate;
  }
}
