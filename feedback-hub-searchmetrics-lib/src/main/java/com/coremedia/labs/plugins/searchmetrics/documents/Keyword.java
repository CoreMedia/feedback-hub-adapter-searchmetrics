package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class Keyword {
  private String id;
  private String keyword;
  private String category;
  @JsonProperty("current_frequency")
  private int currentFrequency;

  @JsonProperty("target_frequency")
  private int targetFrequency;

  @JsonProperty("example_phrases")
  private List<String> examplePhrases;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
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

  public List<String> getExamplePhrases() {
    return examplePhrases;
  }

  public void setExamplePhrases(List<String> examplePhrases) {
    this.examplePhrases = examplePhrases;
  }
}
