package com.coremedia.blueprint.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class BriefingInfos {

  @JsonProperty("content_score_goal")
  private int contentScoreGoal;

  public int getContentScoreGoal() {
    return contentScoreGoal;
  }

  public void setContentScoreGoal(int contentScoreGoal) {
    this.contentScoreGoal = contentScoreGoal;
  }
}
