package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.Briefing;
import com.coremedia.ui.data.ValueExpression;

import ext.StringUtil;

import ext.container.Container;

public class HeaderBase extends Container {

  [Bindable]
  public var briefingExpression:ValueExpression;

  public function HeaderBase(config:Header = null) {
    super(config);
  }

  protected function openTarget():void {
    var url:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_button_url');
    var briefing:Briefing = briefingExpression.getValue();
    url = StringUtil.format(url, briefing.getBriefingId());
    window.open(url, '_blank');
  }
}
}
