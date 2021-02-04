package com.coremedia.labs.plugins.feedbackhub.searchmetrics.model {
import com.coremedia.labs.plugins.feedbackhub.searchmetrics.SearchmetricsPropertyNames;
import com.coremedia.ui.data.impl.BeanImpl;

public class Briefing extends BeanImpl {

  public function Briefing(json:Object) {
    super(json);
  }

  public function getBriefingId():String {
    return get(SearchmetricsPropertyNames.ID);
  }

  public function getName():String {
    return get(SearchmetricsPropertyNames.NAME);
  }

  public function getContent():String {
    return get(SearchmetricsPropertyNames.CONTENT);
  }

  public function getLanguage():String {
    return get(SearchmetricsPropertyNames.LANGUAGE);
  }
}
}
