package com.coremedia.blueprint.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class TopicsCoverage {
  private String topic;

  @JsonProperty("keywords_coverage")
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
