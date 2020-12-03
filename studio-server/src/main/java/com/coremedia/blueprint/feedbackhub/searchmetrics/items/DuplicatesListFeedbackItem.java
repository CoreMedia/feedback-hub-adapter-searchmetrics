package com.coremedia.blueprint.feedbackhub.searchmetrics.items;

import com.coremedia.blueprint.searchmetrics.documents.DuplicateCheckResult;
import com.coremedia.feedbackhub.items.FeedbackItem;

import java.util.List;

/**
 *
 */
public class DuplicatesListFeedbackItem implements FeedbackItem {
  private final String collection;
  private final String title;
  private final String help;
  private final String briefingId;
  private final List<DuplicateCheckResult> duplicationCheckResults;

  public DuplicatesListFeedbackItem(String collection, String title, String help, String briefingId, List<DuplicateCheckResult> duplicationCheckResults) {
    this.collection = collection;
    this.title = title;
    this.help = help;
    this.briefingId = briefingId;
    this.duplicationCheckResults = duplicationCheckResults;
  }

  public String getBriefingId() {
    return briefingId;
  }

  public String getTitle() {
    return title;
  }

  public String getHelp() {
    return help;
  }

  public List<DuplicateCheckResult> getDuplicationCheckResults() {
    return duplicationCheckResults;
  }

  @Override
  public String getCollection() {
    return this.collection;
  }

  @Override
  public String getType() {
    return "searchmetricsDuplicatesList";
  }
}
