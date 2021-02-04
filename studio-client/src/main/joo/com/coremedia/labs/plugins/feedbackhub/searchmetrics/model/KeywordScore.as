package com.coremedia.labs.plugins.feedbackhub.searchmetrics.model {
import com.coremedia.labs.plugins.feedbackhub.searchmetrics.SearchmetricsPropertyNames;
import com.coremedia.ui.data.impl.BeanImpl;

public class KeywordScore extends BeanImpl {

  public function KeywordScore(json:Object) {
    super(json);
  }

  public function getTermId():String {
    return get(SearchmetricsPropertyNames.TERM_ID);
  }
}
}
