package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.blueprint.searchmetrics.documents.BriefingInfo;
import com.coremedia.blueprint.searchmetrics.documents.ContentValidation;
import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.provider.FeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * {@link FeedbackItem} that returns Searchmetrics feedback
 */
@DefaultAnnotation(NonNull.class)
public class SearchmetricsFeedbackItem implements FeedbackItem {

  public static final String TYPE = "searchmetrics";

  private SearchMetricsFeedback feedback;

  SearchmetricsFeedbackItem(Content content, String propertyName, List<BriefingInfo> briefings, Briefing briefing, ContentValidation contentValidation) {
    this.feedback = new SearchMetricsFeedback(content, propertyName, briefings, briefing, contentValidation);
  }

  @Override
  public String getType() {
    return TYPE;
  }

  public SearchMetricsFeedback getFeedback() {
    return feedback;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchmetricsFeedbackItem that = (SearchmetricsFeedbackItem) o;
    return Objects.equals(this, that);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    return "SearchmetricsFeedbackItem";
  }
}
