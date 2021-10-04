package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items;

import com.coremedia.labs.plugins.searchmetrics.documents.DuplicationInformation;
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
  private final List<DuplicationInformation> duplicationInformation;

  public DuplicatesListFeedbackItem(String collection, String title, String help, String briefingId, List<DuplicationInformation> duplicationInformation) {
    this.collection = collection;
    this.title = title;
    this.help = help;
    this.briefingId = briefingId;
    this.duplicationInformation = duplicationInformation;
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

  public List<DuplicationInformation> getDuplicationInformation() {
    return duplicationInformation;
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
