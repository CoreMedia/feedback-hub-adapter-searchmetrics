package com.coremedia.labs.plugins.feedbackhub.searchmetrics.itemtypes {
import com.coremedia.labs.plugins.feedbackhub.searchmetrics.model.Briefing;
import com.coremedia.ui.data.ValueExpression;

import ext.container.Container;

[ResourceBundle('com.coremedia.labs.plugins.feedbackhub.searchmetrics.FeedbackHubSearchmetrics')]
public class QuestionPanelBase extends Container {

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var briefing:Briefing;

  public function QuestionPanelBase(config:QuestionPanel = null) {
    super(config);
  }

  protected function groupTransformer(name:String):String {
    if (name === null) {
      name = resourceManager.getString('com.coremedia.labs.plugins.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_other_questions_' + briefing.getLanguage());
      if (!name) {
        name = resourceManager.getString('com.coremedia.labs.plugins.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_other_questions');
      }
    }
    return name;
  }
}
}
