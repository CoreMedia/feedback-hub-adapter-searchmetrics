package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class BriefingInfos {

  @SerializedName("content_score_goal")
  private int contentScoreGoal;

  public int getContentScoreGoal() {
    return contentScoreGoal;
  }

  public void setContentScoreGoal(int contentScoreGoal) {
    this.contentScoreGoal = contentScoreGoal;
  }
}
