package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class ContentOptResult {
  private int contentOptScore;
  private String keyword;

  @SerializedName("MUST_HAVE")
  private List<KeywordScore> mustHave;

  @SerializedName("ADDITIONAL")
  private List<KeywordScore> additional;

  @SerializedName("RELEVANCE")
  private List<KeywordScore> relevance;

  public int getContentOptScore() {
    return contentOptScore;
  }

  public void setContentOptScore(int contentOptScore) {
    this.contentOptScore = contentOptScore;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public List<KeywordScore> getMustHave() {
    return mustHave;
  }

  public void setMustHave(List<KeywordScore> mustHave) {
    this.mustHave = mustHave;
  }

  public List<KeywordScore> getAdditional() {
    return additional;
  }

  public void setAdditional(List<KeywordScore> additional) {
    this.additional = additional;
  }

  public List<KeywordScore> getRelevance() {
    return relevance;
  }

  public void setRelevance(List<KeywordScore> relevance) {
    this.relevance = relevance;
  }
}
