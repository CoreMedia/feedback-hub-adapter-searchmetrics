package com.coremedia.blueprint.searchmetrics.documents;

import java.util.List;

/**
 *
 */
public class ContentValidation {
  private String id;
  private String title;
  private String content;
  private float languageScore;
  private float overallScore;
  private float readability;
  private int length;
  private int targetLength;
  private int targetOverallScore;
  private List<ContentOptResult> contentOptResults;
  private ContentScore contentScore;
  private List<DuplicateCheckResult> duplicationCheckResults;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public float getOverallScore() {
    return overallScore;
  }

  public void setOverallScore(float overallScore) {
    this.overallScore = overallScore;
  }

  public int getTargetOverallScore() {
    return targetOverallScore;
  }

  public void setTargetOverallScore(int targetOverallScore) {
    this.targetOverallScore = targetOverallScore;
  }

  public ContentScore getContentScore() {
    return contentScore;
  }

  public void setContentScore(ContentScore contentScore) {
    this.contentScore = contentScore;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public float getLanguageScore() {
    return languageScore;
  }

  public void setLanguageScore(float languageScore) {
    this.languageScore = languageScore;
  }

  public List<ContentOptResult> getContentOptResults() {
    return contentOptResults;
  }

  public void setContentOptResults(List<ContentOptResult> contentOptResults) {
    this.contentOptResults = contentOptResults;
  }

  public float getReadability() {
    return readability;
  }

  public void setReadability(float readability) {
    this.readability = readability;
  }

  public int getTargetLength() {
    return targetLength;
  }

  public void setTargetLength(int targetLength) {
    this.targetLength = targetLength;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public List<DuplicateCheckResult> getDuplicationCheckResults() {
    return duplicationCheckResults;
  }

  public void setDuplicationCheckResults(List<DuplicateCheckResult> duplicationCheckResults) {
    this.duplicationCheckResults = duplicationCheckResults;
  }
}
