package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.feedbackhub.adapter.FeedbackHubErrorCode;

/**
 * Error codes for Searchmetrics adapter
 */
enum SearchmetricsFeedbackHubErrorCode implements FeedbackHubErrorCode {
  API_KEY_NOT_SET,
  API_SECRET_NOT_SET,
  PROJECT_ID_NOT_SET,
  PROPERTY_NOT_SET
}
