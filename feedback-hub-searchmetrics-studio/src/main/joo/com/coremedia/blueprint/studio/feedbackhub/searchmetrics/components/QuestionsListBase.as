package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.Briefing;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.Question;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.ComponentManager;
import ext.StringUtil;
import ext.container.Container;

public class QuestionsListBase extends Container {

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var propertyName:String;

  public function QuestionsListBase(config:QuestionsList = null) {
    super(config);
  }


  override protected function afterRender():void {
    super.afterRender();

    var parent:Container = queryById("question-list") as Container;
    var questions:Array = getQuestions(this.initialConfig);
    var lastGroup:String = null;
    for each(var q:ValueExpression in questions) {
      var baseConfig:Object = {
        bindTo: q,
        xtype: QuestionPanel.xtype,
        briefing: bindTo.getValue()
      };

      if (lastGroup === null) {
        lastGroup = getGroupName(q.getValue());
      }
      else if (lastGroup !== getGroupName(q.getValue())) {
        lastGroup = getGroupName(q.getValue());

        //TODO we have no skin for these kinf of spacers
        var spacerCfg:Object = {
          style: 'border-top: solid 1px #b3b1b1;margin-top:12px;',
          height: 20,
          xtype: 'container'
        };

        var spacer:Container = ComponentManager.create(spacerCfg) as Container;
        parent.add(spacer);
      }

      var questionPanel:QuestionPanel = ComponentManager.create(baseConfig) as QuestionPanel;
      parent.add(questionPanel);
    }
  }

  private function getGroupName(q:Question):String {
    var name:String = q.getGroup();
    if (name === null || name === undefined || name.length === 0) {
      var briefing:Briefing = bindTo.getValue();
      name = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_other_questions_' + briefing.getLanguage());
      if(!name) {
        name = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_other_questions');
      }
    }
    return name;
  }

  protected function getQuestions(config:*):Array {
    var result:Array = [];
    var questions:Array = config.bindTo.extendBy(config.propertyName).getValue();
    if (questions) {
      var data:Array = questions[0].data;
      if (data) {
        for each(var q:Object in data) {
          result.push(ValueExpressionFactory.createFromValue(new Question(q)));
        }
      }
    }
    return result;
  }

  protected function questionCounterTransformer(questions:Array):String {
    if (questions) {
      var msg:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_questions');
      return StringUtil.format(msg, questions.length);
    }
    return "";
  }
}
}
