package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics;

import com.coremedia.feedbackhub.settings.FeedbackSettingsProvider;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;

public class SearchmetricsSettingsProvider {

  private final FeedbackSettingsProvider feedbackSettingsProvider;

  private final String factoryId;

  public SearchmetricsSettingsProvider(FeedbackSettingsProvider feedbackSettingsProvider, String factoryId) {
    this.factoryId = factoryId;
    this.feedbackSettingsProvider = feedbackSettingsProvider;
  }

  public SearchmetricsSettings getSettings(String groupId, String siteId) {
    return feedbackSettingsProvider.getSettings(SearchmetricsSettings.class, factoryId, groupId, siteId);
  }

}
