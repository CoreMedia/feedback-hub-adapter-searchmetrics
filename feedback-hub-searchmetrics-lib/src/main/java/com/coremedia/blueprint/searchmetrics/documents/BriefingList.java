package com.coremedia.blueprint.searchmetrics.documents;

import java.util.List;

/**
 *
 */
public class BriefingList {
  private int count;
  private List<BriefingInfo> briefings;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<BriefingInfo> getBriefings() {
    return briefings;
  }

  public void setBriefings(List<BriefingInfo> briefings) {
    this.briefings = briefings;
  }
}
