package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class TopicsCoverage {
  private String topic;

  @SerializedName("keywords_coverage")
  private KeywordsCoverage keywordsCoverage;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public KeywordsCoverage getKeywordsCoverage() {
    return keywordsCoverage;
  }

  public void setKeywordsCoverage(KeywordsCoverage keywordsCoverage) {
    this.keywordsCoverage = keywordsCoverage;
  }
}
