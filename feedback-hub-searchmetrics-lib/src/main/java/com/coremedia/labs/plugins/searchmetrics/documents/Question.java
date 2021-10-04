package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class Question {
  @JsonProperty("question_id")
  private String questionId;

  @JsonProperty("question_text")
  private String questionText;

  @JsonProperty("is_selected")
  private boolean isSelected;

  @JsonProperty("question_type")
  private String questionType;

  @JsonProperty("local_rank")
  private int localRank;

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }

  public int getLocalRank() {
    return localRank;
  }

  public void setLocalRank(int localRank) {
    this.localRank = localRank;
  }
}
