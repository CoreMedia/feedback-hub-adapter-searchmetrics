package com.coremedia.labs.plugins.feedbackhub.searchmetrics.model {
import com.coremedia.labs.plugins.feedbackhub.searchmetrics.SearchmetricsPropertyNames;
import com.coremedia.ui.data.impl.BeanImpl;

public class BriefingInfo extends BeanImpl {

  public function BriefingInfo(json:Object) {
    super(json);
  }

  public function getBriefingId():String {
    return get(SearchmetricsPropertyNames.ID);
  }

  public function getStory():String {
    return get(SearchmetricsPropertyNames.STORY);
  }

  public function setStory(name:String):String {
    set(SearchmetricsPropertyNames.STORY, name);
  }
}
}
