package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics;

import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapter;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapterFactory;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import org.apache.commons.lang3.StringUtils;

public class SearchmetricsFeedbackAdapterFactory implements FeedbackHubAdapterFactory<SearchmetricsSettings> {
  public static final String TYPE = "searchmetrics";
  private SearchmetricsService service;

  public SearchmetricsFeedbackAdapterFactory(SearchmetricsService service) {
    this.service = service;
  }

  @Override
  public String getId() {
    return TYPE;
  }

  @Override
  public FeedbackHubAdapter create(SearchmetricsSettings settings) {
    String apiKey = settings.getApiKey();
    if (StringUtils.isEmpty(apiKey)) {
      throw new FeedbackHubException("settings must provide an apiKey", SearchmetricsFeedbackHubErrorCode.API_KEY_NOT_SET);
    }

    String apiSecret = settings.getSharedSecret();
    if (StringUtils.isEmpty(apiSecret)) {
      throw new FeedbackHubException("settings must provide an apiSecret", SearchmetricsFeedbackHubErrorCode.API_SECRET_NOT_SET);
    }

    Integer projectId = settings.getProjectId();
    if (projectId == null) {
      throw new FeedbackHubException("settings must provide an projectId", SearchmetricsFeedbackHubErrorCode.PROJECT_ID_NOT_SET);
    }

    return new SearchmetricsFeedbackAdapter(settings, service);
  }
}
