package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class BriefingInfo {
  private String id;
  private String story;

  @SerializedName("text_length")
  private int textLength;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStory() {
    return story;
  }

  public void setStory(String story) {
    this.story = story;
  }

  public int getTextLength() {
    return textLength;
  }

  public void setTextLength(int textLength) {
    this.textLength = textLength;
  }
}
