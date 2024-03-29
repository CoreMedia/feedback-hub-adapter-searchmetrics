package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class ContentExperience {

  @SerializedName("briefings_list")
  private BriefingList briefingsList;

  private Briefing briefing;

  private String id;

  public Briefing getBriefing() {
    return briefing;
  }

  public void setBriefing(Briefing briefing) {
    this.briefing = briefing;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BriefingList getBriefingsList() {
    return briefingsList;
  }

  public void setBriefingsList(BriefingList briefingsList) {
    this.briefingsList = briefingsList;
  }
}
