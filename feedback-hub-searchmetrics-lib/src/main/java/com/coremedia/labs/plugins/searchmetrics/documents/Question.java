package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Question {
  private String id;
  private String question;

  @SerializedName("local_rank")
  private int localRank;
  @SerializedName("global_rank")
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
