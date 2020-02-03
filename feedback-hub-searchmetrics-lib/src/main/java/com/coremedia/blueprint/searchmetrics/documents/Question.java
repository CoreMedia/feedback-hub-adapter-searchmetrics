package com.coremedia.blueprint.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class Question {
  private String id;
  private String question;

  @JsonProperty("local_rank")
  private int localRank;
  @JsonProperty("global_rank")
  private int globalRank;

  private String group;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public int getLocalRank() {
    return localRank;
  }

  public void setLocalRank(int localRank) {
    this.localRank = localRank;
  }

  public int getGlobalRank() {
    return globalRank;
  }

  public void setGlobalRank(int globalRank) {
    this.globalRank = globalRank;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }
}
