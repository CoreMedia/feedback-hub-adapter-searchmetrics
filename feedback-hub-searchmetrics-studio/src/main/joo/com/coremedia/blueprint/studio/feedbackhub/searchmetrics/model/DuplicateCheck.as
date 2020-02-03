package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.SearchmetricsPropertyNames;
import com.coremedia.ui.data.impl.BeanImpl;

public class DuplicateCheck extends BeanImpl {

  public function DuplicateCheck(json:Object) {
    super(json);
  }

  public function getUrl():String {
    return get(SearchmetricsPropertyNames.URL);
  }

  public function getDuplicationScore():Number {
    var number:Number = get(SearchmetricsPropertyNames.DUPLICATION_SCORE);
    if(number < 0) {
      return 0;
    }
    return number;
  }
}
}
