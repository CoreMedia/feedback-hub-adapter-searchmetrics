package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics;

import com.coremedia.cms.common.plugins.beans_for_plugins2.CommonBeansForPluginsConfiguration;
import com.coremedia.feedbackhub.beans_for_plugins.FeedbackHubBeansForPluginsConfiguration;
import com.coremedia.feedbackhub.settings.FeedbackSettingsProvider;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs.AssignBriefingJobFactory;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs.GetBriefingDetailsJobFactory;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs.GetBriefingsJobFactory;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsServiceConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SearchmetricsServiceConfiguration.class,
        CommonBeansForPluginsConfiguration.class,
        FeedbackHubBeansForPluginsConfiguration.class
})
public class SearchmetricsFeedbackHubConfiguration {

    @Bean
    public SearchmetricsFeedbackAdapterFactory searchmetricsContentFeedbackProviderFactory(@NonNull SearchmetricsService searchmetricsService) {
        return new SearchmetricsFeedbackAdapterFactory(searchmetricsService);
    }

    @Bean
    public SearchmetricsSettingsProvider searchmetricsFeedbackSettingsProvider(FeedbackSettingsProvider feedbackSettingsProvider) {
        return new SearchmetricsSettingsProvider(feedbackSettingsProvider, SearchmetricsFeedbackAdapterFactory.TYPE);
    }

    @Bean
    public GetBriefingDetailsJobFactory getBriefingDetailsPageJobFactory(@NonNull SearchmetricsService searchmetricsService,
                                                                         @NonNull SearchmetricsSettingsProvider searchmetricsFeedbackSettingsProvider) {
        return new GetBriefingDetailsJobFactory(searchmetricsFeedbackSettingsProvider, searchmetricsService);
    }

    @Bean
    public GetBriefingsJobFactory getBriefingsPageJobFactory(@NonNull SearchmetricsService searchmetricsService,
                                                             @NonNull SearchmetricsSettingsProvider searchmetricsFeedbackSettingsProvider) {
        return new GetBriefingsJobFactory(searchmetricsService, searchmetricsFeedbackSettingsProvider);
    }

    @Bean
    public AssignBriefingJobFactory assignBriefingJobFactory(@NonNull SearchmetricsService searchmetricsService,
                                                             @NonNull SearchmetricsSettingsProvider searchmetricsFeedbackSettingsProvider) {
        return new AssignBriefingJobFactory(searchmetricsService, searchmetricsFeedbackSettingsProvider);
    }
}
