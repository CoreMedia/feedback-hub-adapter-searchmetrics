package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items;

import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.labs.plugins.searchmetrics.documents.Keyword;

import java.util.List;

/**
 *
 */
public class KeywordListFeedbackItem implements FeedbackItem {
  public static final String MUST_HAVE = "MUST_HAVE";
  public static final String RECOMMENDED = "RECOMMENDED";
  public static final String ADDITIONAL = "ADDITIONAL";

  private final String collection;
  private final String title;
  private final List<Keyword> keywords;
  private final String keywordType;
  private final String help;

  public KeywordListFeedbackItem(String collection, String title, List<Keyword> keywords, String keywordType, String help) {
    this.collection = collection;
    this.title = title;
    this.keywords = keywords;
    this.keywordType = keywordType;
    this.help = help;
  }

  public List<Keyword> getKeywords() {
    return keywords;
  }

  public String getKeywordType() {
    return keywordType;
  }

  @Override
  public String getTitle() {
    return title;
  }

  public String getHelp() {
    return help;
  }

  @Override
  public String getCollection() {
    return this.collection;
  }

  @Override
  public String getType() {
    return "searchmetricsKeywordList";
  }
}
