package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *{
 *   "content_version_id":"c8d1262ca571-4a6c-95a0-87821a58eec1",
 *   "content_score":0.9,
 *   "coverage_score":0.9,
 *   "natural_language_score":0.9,
 *   "repetition_score":0.9,
 *   "word_count":255,
 *   "title":"Avocado Recipes in June",
 *   "content":"<p>This is a very good text about avocado recipes</p>",
 *   "author_id":"e5745d6ra82y638d51047691a2b0e70b",
 *   "meta_title":"Avocado recipes",
 *   "meta_description":"Excellent avocado recipes for this years summer season",
 *   "length_score":0.9,
 *   "readability_score":0.9,
 *   "created_at":"2007-12-03T10:15:30+01:00",
 *   "modified_at":"2007-12-03T10:15:30+01:00"
 * }
 */
public class ContentVersion {
  @JsonProperty("content_version_id")
  private float contentVersionI;

  @JsonProperty("content_score")
  private float contentScore;

  @JsonProperty("coverage_score")
  private float coverageScore;

  @JsonProperty("natural_language_score")
  private float naturalLanguageScore;

  @JsonProperty("repetition_score")
  private float repetitionScore;

  @JsonProperty("word_count")
  private int wordCount;

  private String title;

  private String content;

  @JsonProperty("meta_title")
  private int metaTitle;

  @JsonProperty("meta_description")
  private int metaDecription;

  @JsonProperty("length_score")
  private float lengthScore;

  @JsonProperty("readability_score")
  private float readabilityScore;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("modified_at")
  private String modifiedAt;

  public float getContentVersionI() {
    return contentVersionI;
  }

  public void setContentVersionI(float contentVersionI) {
    this.contentVersionI = contentVersionI;
  }

  public float getContentScore() {
    return contentScore;
  }

  public void setContentScore(float contentScore) {
    this.contentScore = contentScore;
  }

  public float getCoverageScore() {
    return coverageScore;
  }

  public void setCoverageScore(float coverageScore) {
    this.coverageScore = coverageScore;
  }

  public float getNaturalLanguageScore() {
    return naturalLanguageScore;
  }

  public void setNaturalLanguageScore(float naturalLanguageScore) {
    this.naturalLanguageScore = naturalLanguageScore;
  }

  public float getRepetitionScore() {
    return repetitionScore;
  }

  public void setRepetitionScore(float repetitionScore) {
    this.repetitionScore = repetitionScore;
  }

  public int getWordCount() {
    return wordCount;
  }

  public void setWordCount(int wordCount) {
    this.wordCount = wordCount;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getMetaTitle() {
    return metaTitle;
  }

  public void setMetaTitle(int metaTitle) {
    this.metaTitle = metaTitle;
  }

  public int getMetaDecription() {
    return metaDecription;
  }

  public void setMetaDecription(int metaDecription) {
    this.metaDecription = metaDecription;
  }

  public float getLengthScore() {
    return lengthScore;
  }

  public void setLengthScore(float lengthScore) {
    this.lengthScore = lengthScore;
  }

  public float getReadabilityScore() {
    return readabilityScore;
  }

  public void setReadabilityScore(float readabilityScore) {
    this.readabilityScore = readabilityScore;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(String modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
}
