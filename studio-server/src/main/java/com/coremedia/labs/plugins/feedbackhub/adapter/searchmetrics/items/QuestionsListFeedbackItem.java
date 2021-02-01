package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items;

import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 *
 */
public class QuestionsListFeedbackItem implements FeedbackItem {
  private final String collection;
  private final String title;
  private final Briefing briefing;

  public QuestionsListFeedbackItem(String collection, String title, Briefing briefing) {
    this.collection = collection;
    this.title = title;
    this.briefing = briefing;
  }

  @Override
  public String getTitle() {
    return title;
  }

  public Briefing getBriefing() {
    return briefing;
  }

  @Override
  public String getCollection() {
    return this.collection;
  }

  @Override
  public String getType() {
    return "searchmetricsQuestionsList";
  }
}
