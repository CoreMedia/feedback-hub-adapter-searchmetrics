package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics;

import com.coremedia.feedbackhub.adapter.FeedbackHubErrorCode;

/**
 * Error codes for Searchmetrics adapter
 */
enum SearchmetricsFeedbackHubErrorCode implements FeedbackHubErrorCode {
  CLIENT_ID_NOT_SET,
  CLIENT_SECRET_NOT_SET,
  REDIRECT_NOT_SET
}
