package com.coremedia.labs.plugins.searchmetrics.documents;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class QueryData {

  @SerializedName("content_experience")
  private ContentExperience contentExperience;

  @SerializedName("content_validation")
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
