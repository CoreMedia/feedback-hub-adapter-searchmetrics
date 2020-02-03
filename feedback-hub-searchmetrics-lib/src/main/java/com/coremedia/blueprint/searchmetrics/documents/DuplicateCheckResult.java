package com.coremedia.blueprint.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class DuplicateCheckResult {
  @JsonProperty("duplication_score")
  private int duplicationScore;

  private String level;
  private String title;
  private String url;

  public int getDuplicationScore() {
    if(duplicationScore < 0) {
      return 0;
    }
    return duplicationScore;
  }

  public void setDuplicationScore(int duplicationScore) {
    this.duplicationScore = duplicationScore;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
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
