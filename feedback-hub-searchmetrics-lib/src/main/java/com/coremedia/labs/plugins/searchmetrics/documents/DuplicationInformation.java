package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class DuplicationInformation {
  @JsonProperty("duplication_score")
  private int duplicationScore;

  private String title;
  private String url;

  public int getDuplicationScore() {
    return duplicationScore;
  }

  public void setDuplicationScore(int duplicationScore) {
    this.duplicationScore = duplicationScore;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
