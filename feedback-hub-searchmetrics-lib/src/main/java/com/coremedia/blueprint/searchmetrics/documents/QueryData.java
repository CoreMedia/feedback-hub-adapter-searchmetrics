package com.coremedia.blueprint.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class QueryData {

  @JsonProperty("content_experience")
  private ContentExperience contentExperience;

  @JsonProperty("content_validation")
  private ContentValidation contentValidation;

  public ContentExperience getContentExperience() {
    return contentExperience;
  }

  public void setContentExperience(ContentExperience contentExperience) {
    this.contentExperience = contentExperience;
  }

  public ContentValidation getContentValidation() {
    return contentValidation;
  }

  public void setContentValidation(ContentValidation contentValidation) {
    this.contentValidation = contentValidation;
  }
}
