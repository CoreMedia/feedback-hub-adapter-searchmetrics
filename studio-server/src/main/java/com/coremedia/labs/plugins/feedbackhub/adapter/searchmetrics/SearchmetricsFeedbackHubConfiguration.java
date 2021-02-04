package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics;

import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs.AssignBriefingJobFactory;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs.GetBriefingDetailsJobFactory;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs.GetBriefingsJobFactory;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsServiceConfiguration;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.feedbackhub.FeedbackHubConfigurationProperties;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SearchmetricsServiceConfiguration.class,
        CommonBeansForPluginsConfiguration.class})
public class SearchmetricsFeedbackHubConfiguration {

  @Bean
  public SearchmetricsFeedbackAdapterFactory searchmetricsContentFeedbackProviderFactory(@NonNull SearchmetricsService searchmetricsService) {
    return new SearchmetricsFeedbackAdapterFactory(searchmetricsService);
  }

  @Bean
  public FeedbackSettingsProvider searchmetricsFeedbackSettingsProvider(@NonNull SitesService sitesService, @NonNull CapConnection capConnection) {
    return new FeedbackSettingsProvider(capConnection,
            sitesService,
            new FeedbackHubConfigurationProperties.Bindings(),
            SearchmetricsSettings.class,
            SearchmetricsFeedbackAdapterFactory.TYPE);
  }

  @Bean
  public GetBriefingDetailsJobFactory getBriefingDetailsPageJobFactory(@NonNull SearchmetricsService searchmetricsService,
                                                                       @NonNull FeedbackSettingsProvider searchmetricsFeedbackSettingsProvider) {
    return new GetBriefingDetailsJobFactory(searchmetricsFeedbackSettingsProvider, searchmetricsService);
  }

  @Bean
  public GetBriefingsJobFactory getBriefingsPageJobFactory(@NonNull SearchmetricsService searchmetricsService,
                                                           @NonNull FeedbackSettingsProvider searchmetricsFeedbackSettingsProvider) {
    return new GetBriefingsJobFactory(searchmetricsService, searchmetricsFeedbackSettingsProvider);
  }

  @Bean
  public AssignBriefingJobFactory assignBriefingJobFactory(@NonNull SearchmetricsService searchmetricsService,
                                                           @NonNull FeedbackSettingsProvider searchmetricsFeedbackSettingsProvider) {
    return new AssignBriefingJobFactory(searchmetricsService, searchmetricsFeedbackSettingsProvider);
  }
}
