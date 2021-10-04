package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {
 *   "title": "Avocado Recipes in June",
 *   "content": "<p>This is a very good text about avocado recipes</p>",
 *   "author_id": "e5745d6ra82y638d51047691a2b0e70b",
 *   "meta_title": "Avocado recipes",
 *   "meta_description": "Excellent avocado recipes for this years summer season",
 *   "content_sequence_id": 12345
 * }
 */
public class ContentUpdate {
  private String title;
  private String content;

  @JsonProperty("author_id")
  private String authorId;

  @JsonProperty("meta_title")
  private String metaTitle;

  @JsonProperty("meta_description")
  private String metaDescription;

  @JsonProperty("content_sequence_id")
  private int contentSequenceId;

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

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getMetaTitle() {
    return metaTitle;
  }

  public void setMetaTitle(String metaTitle) {
    this.metaTitle = metaTitle;
  }

  public String getMetaDescription() {
    return metaDescription;
  }

  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  public int getContentSequenceId() {
    return contentSequenceId;
  }

  public void setContentSequenceId(int contentSequenceId) {
    this.contentSequenceId = contentSequenceId;
  }
}
