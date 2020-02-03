package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.blueprint.searchmetrics.documents.BriefingInfo;
import com.coremedia.blueprint.searchmetrics.documents.ContentValidation;
import com.coremedia.cap.content.Content;

import java.util.List;

/**
 *
 */
public class SearchMetricsFeedback {

  private List<BriefingInfo> briefings;
  private Briefing briefing;
  private ContentValidation contentValidation;
  private Content content;
  private String propertyName;

  public SearchMetricsFeedback(Content content, String propertyName, List<BriefingInfo> briefings, Briefing briefing, ContentValidation contentValidation) {
    this.content = content;
    this.propertyName = propertyName;
    this.briefings = briefings;
    this.briefing = briefing;
    this.contentValidation = contentValidation;
  }

  public List<BriefingInfo> getBriefings() {
    return briefings;
  }

  public Briefing getBriefing() {
    return briefing;
  }

  public ContentValidation getContentValidation() {
    return contentValidation;
  }

  public Content getContent() {
    return content;
  }

  public String getPropertyName() {
    return propertyName;
  }
}
