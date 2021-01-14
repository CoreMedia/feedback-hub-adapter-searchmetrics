package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.blueprint.feedbackhub.searchmetrics.jobs.AssignBriefingJobFactory;
import com.coremedia.blueprint.feedbackhub.searchmetrics.jobs.GetBriefingDetailsJobFactory;
import com.coremedia.blueprint.feedbackhub.searchmetrics.jobs.GetBriefingsJobFactory;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsServiceConfiguration;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
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
  public GetBriefingDetailsJobFactory getBriefingDetailsPageJobFactory(@NonNull SearchmetricsService searchmetricsService, @NonNull SitesService sitesService) {
    return new GetBriefingDetailsJobFactory(searchmetricsService, sitesService);
  }

  @Bean
  public GetBriefingsJobFactory getBriefingsPageJobFactory(@NonNull SearchmetricsService searchmetricsService, @NonNull SitesService sitesService) {
    return new GetBriefingsJobFactory(searchmetricsService, sitesService);
  }

  @Bean
  public AssignBriefingJobFactory assignBriefingJobFactory(@NonNull SearchmetricsService searchmetricsService, @NonNull SitesService sitesService) {
    return new AssignBriefingJobFactory(searchmetricsService, sitesService);
  }
}
