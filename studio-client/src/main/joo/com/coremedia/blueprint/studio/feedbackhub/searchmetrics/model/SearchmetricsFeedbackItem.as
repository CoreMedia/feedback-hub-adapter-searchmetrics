package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model {
import com.coremedia.cms.studio.feedbackhub.model.*;

public class SearchmetricsFeedbackItem extends FeedbackItem {
  public var feedback:Object;

  //noinspection ReservedWordAsName
  public function SearchmetricsFeedbackItem(id:String, type:String, collection:String, feedback:Object) {
    super(id, type, null, collection);
    this.feedback = feedback;
  }

  public function getContentValidation():ContentValidation {
    if(this.feedback.contentValidation) {
      return new ContentValidation(this.feedback.contentValidation);
    }

    return null;
  }
}
}
