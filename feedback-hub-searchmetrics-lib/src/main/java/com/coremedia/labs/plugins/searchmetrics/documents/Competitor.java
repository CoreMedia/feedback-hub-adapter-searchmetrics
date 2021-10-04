package com.coremedia.labs.plugins.searchmetrics.documents;

/**
 *
 */
public class Competitor {
  private String url;
  private String title;
  private int position;
  private int wordCount;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getWordCount() {
    return wordCount;
  }

  public void setWordCount(int wordCount) {
    this.wordCount = wordCount;
  }
}
