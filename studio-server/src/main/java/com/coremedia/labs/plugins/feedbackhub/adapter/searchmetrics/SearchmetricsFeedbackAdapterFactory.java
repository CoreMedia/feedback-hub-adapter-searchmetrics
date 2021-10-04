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
    String clientId = settings.getClientId();
    if (StringUtils.isEmpty(clientId)) {
      throw new FeedbackHubException("settings must provide an clientId", SearchmetricsFeedbackHubErrorCode.CLIENT_ID_NOT_SET);
    }

    String clientSecret = settings.getClientSecret();
    if (StringUtils.isEmpty(clientSecret)) {
      throw new FeedbackHubException("settings must provide an clientSecret", SearchmetricsFeedbackHubErrorCode.CLIENT_SECRET_NOT_SET);
    }

    String redirectUrl = settings.getRedirectUrl();
    if (redirectUrl == null) {
      throw new FeedbackHubException("settings must provide an redirectUrl", SearchmetricsFeedbackHubErrorCode.REDIRECT_NOT_SET);
    }

    return new SearchmetricsFeedbackAdapter(settings, service);
  }
}
