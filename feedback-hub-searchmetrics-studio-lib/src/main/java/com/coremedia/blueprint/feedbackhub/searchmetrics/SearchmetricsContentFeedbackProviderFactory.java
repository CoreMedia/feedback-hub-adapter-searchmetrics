package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import com.coremedia.feedbackhub.provider.ContentFeedbackProvider;
import com.coremedia.feedbackhub.provider.ContentFeedbackProviderFactory;

public class SearchmetricsContentFeedbackProviderFactory implements ContentFeedbackProviderFactory<SearchmetricsSettings> {
  private SearchmetricsService service;
  private SearchmetricsSettings settings;

  public SearchmetricsContentFeedbackProviderFactory(SearchmetricsService service) {
    this.service = service;
  }

  @Override
  public String getId() {
    return SearchmetricsFeedbackItem.TYPE;
  }

  @Override
  public ContentFeedbackProvider create(SearchmetricsSettings settings) {
    String apiKey = settings.getApiKey();
    if (apiKey == null) {
      throw new FeedbackHubException("settings must provide an apiKey", SearchmetricsFeedbackHubErrorCode.API_KEY_NOT_SET);
    }

    String apiSecret = settings.getSharedSecret();
    if (apiSecret == null) {
      throw new FeedbackHubException("settings must provide an apiSecret", SearchmetricsFeedbackHubErrorCode.API_SECRET_NOT_SET);
    }

    Integer projectId = settings.getProjectId();
    if (projectId == null) {
      throw new FeedbackHubException("settings must provide an projectId", SearchmetricsFeedbackHubErrorCode.PROJECT_ID_NOT_SET);
    }
    this.settings = settings;

    return new SearchmetricsContentFeedbackProvider(settings, service);
  }

  public SearchmetricsSettings getSettings() {
    return settings;
  }
}
