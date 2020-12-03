package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.blueprint.feedbackhub.searchmetrics.config.SearchmetricsConfigResource;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsServiceConfiguration;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.FeedbackService;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

@Configuration
@Import(SearchmetricsServiceConfiguration.class)
@ImportResource(
        value = {
                "classpath:/com/coremedia/cap/multisite/multisite-services.xml"
        },
        reader = ResourceAwareXmlBeanDefinitionReader.class
)
public class SearchmetricsFeedbackHubConfiguration {

  @Bean
  public SearchmetricsFeedbackAdapterFactory searchmetricsContentFeedbackProviderFactory(@NonNull SearchmetricsService searchmetricsService) {
    return new SearchmetricsFeedbackAdapterFactory(searchmetricsService);
  }

  @Bean
  @Scope("prototype")
  SearchmetricsConfigResource searchmetricsConfigResource(SearchmetricsService searchmetricsService,
                                                          FeedbackService feedbackService,
                                                          SitesService sitesService) {
    return new SearchmetricsConfigResource(searchmetricsService, feedbackService, sitesService);
  }
}
