package com.coremedia.blueprint.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class Briefing {
  private String id;
  private String title;
  private String name;
  private String language;
  private String content;

  @JsonProperty("main_topic")
  private String mainTopic;

//  @JsonProperty("topics_coverage")
//  private TopicsCoverage topicsCoverage;

  @JsonProperty("target_score")
  private int targetScore;

  @JsonProperty("target_length")
  private int targetLength;

  @JsonProperty("content_score")
  private int contentScore;

  @JsonProperty("content_length")
  private int contentLength;

  private List<Questions> questions;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMainTopic() {
    return mainTopic;
  }

  public void setMainTopic(String mainTopic) {
    this.mainTopic = mainTopic;
  }

  public int getTargetScore() {
    return targetScore;
  }

  public void setTargetScore(int targetScore) {
    this.targetScore = targetScore;
  }

  public int getTargetLength() {
    return targetLength;
  }

  public void setTargetLength(int targetLength) {
    this.targetLength = targetLength;
  }

  public int getContentScore() {
    return contentScore;
  }

  public void setContentScore(int contentScore) {
    this.contentScore = contentScore;
  }

  public int getContentLength() {
    return contentLength;
  }

  public void setContentLength(int contentLength) {
    this.contentLength = contentLength;
  }

  public List<Questions> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Questions> questions) {
    this.questions = questions;
  }

//  public TopicsCoverage getTopicsCoverage() {
//    return topicsCoverage;
//  }
//
//  public void setTopicsCoverage(TopicsCoverage topicsCoverage) {
//    this.topicsCoverage = topicsCoverage;
//  }
}
