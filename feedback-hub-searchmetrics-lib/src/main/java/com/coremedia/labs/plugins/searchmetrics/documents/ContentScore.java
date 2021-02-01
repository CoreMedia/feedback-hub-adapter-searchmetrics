package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class ContentScore {
  @JsonProperty("content_score")
  private float contentScore;

  @JsonProperty("coverage_score")
  private float coverageScore;

  @JsonProperty("length_score")
  private float lengthScore;

  @JsonProperty("natural_language_score")
  private float naturalLanguageScore;

  @JsonProperty("repetition_score")
  private float repetitionScore;

  public float getContentScore() {
    return contentScore;
  }

  public void setContentScore(float contentScore) {
    this.contentScore = contentScore;
  }

  public float getCoverageScore() {
    return coverageScore;
  }

  public void setCoverageScore(float coverageScore) {
    this.coverageScore = coverageScore;
  }

  public float getLengthScore() {
    return lengthScore;
  }

  public void setLengthScore(float lengthScore) {
    this.lengthScore = lengthScore;
  }

  public float getNaturalLanguageScore() {
    return naturalLanguageScore;
  }

  public void setNaturalLanguageScore(float naturalLanguageScore) {
    this.naturalLanguageScore = naturalLanguageScore;
  }

  public float getRepetitionScore() {
    return repetitionScore;
  }

  public void setRepetitionScore(float repetitionScore) {
    this.repetitionScore = repetitionScore;
  }
}
