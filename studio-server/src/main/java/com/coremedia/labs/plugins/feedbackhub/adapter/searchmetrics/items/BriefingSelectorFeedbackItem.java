package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items;

import com.coremedia.labs.plugins.searchmetrics.documents.BriefingInfo;
import com.coremedia.feedbackhub.items.FeedbackItem;

import java.util.List;

/**
 *
 */
public class BriefingSelectorFeedbackItem implements FeedbackItem {

  private final String collection;
  private List<BriefingInfo> briefingInfos;
  private final String briefingId;
  private final String briefingText;

  public BriefingSelectorFeedbackItem(String collection, List<BriefingInfo> briefingInfos, String briefingId, String briefingText) {
    this.collection = collection;
    this.briefingInfos = briefingInfos;
    this.briefingId = briefingId;
    this.briefingText = briefingText;
  }

  @Override
  public String getType() {
    return "searchmetricsBriefingSelector";
  }

  @Override
  public String getCollection() {
    return collection;
  }

  public List<BriefingInfo> getBriefingInfos() {
    return briefingInfos;
  }

  public String getBriefingText() {
    return briefingText;
  }

  public String getBriefingId() {
    return briefingId;
  }
}
