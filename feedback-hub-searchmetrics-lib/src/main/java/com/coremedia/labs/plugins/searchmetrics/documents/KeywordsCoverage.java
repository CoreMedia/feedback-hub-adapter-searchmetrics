package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class KeywordsCoverage {
  private String keyword;
  private String keywordType;

  @SerializedName("current_frequency")
  private int currentFrequency;

  @SerializedName("target_frequency")
  private int targetFrequency;

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getKeywordType() {
    return keywordType;
  }

  public void setKeywordType(String keywordType) {
    this.keywordType = keywordType;
  }

  public int getCurrentFrequency() {
    return currentFrequency;
  }

  public void setCurrentFrequency(int currentFrequency) {
    this.currentFrequency = currentFrequency;
  }

  public int getTargetFrequency() {
    return targetFrequency;
  }

  public void setTargetFrequency(int targetFrequency) {
    this.targetFrequency = targetFrequency;
  }
}
