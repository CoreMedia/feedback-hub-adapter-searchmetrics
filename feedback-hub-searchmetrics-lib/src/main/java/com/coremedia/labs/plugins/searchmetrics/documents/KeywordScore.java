package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class KeywordScore {
  private int current;
  private String keyword;
  private int target;

  @SerializedName("term_id")
  private String termId;

  public int getCurrent() {
    return current;
  }

  public void setCurrent(int current) {
    this.current = current;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public int getTarget() {
    return target;
  }

  public void setTarget(int target) {
    this.target = target;
  }

  public String getTermId() {
    return termId;
  }

  public void setTermId(String termId) {
    this.termId = termId;
  }
}
