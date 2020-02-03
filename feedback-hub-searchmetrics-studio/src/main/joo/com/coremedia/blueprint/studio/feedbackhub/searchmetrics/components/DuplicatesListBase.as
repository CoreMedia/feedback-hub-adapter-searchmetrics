package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.SearchmetricsPropertyNames;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.DuplicateCheck;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;
import ext.container.Container;

public class DuplicatesListBase extends Container {
  private static const DUPLICATE_THRESHOLD:Number = 16;

  [Bindable]
  public var briefingExpression:ValueExpression;

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var propertyName:String;

  private var questionsExpression:ValueExpression;


  public function DuplicatesListBase(config:DuplicatesList = null) {
    super(config);
  }

  protected function getDuplicatesExpression(config:DuplicatesList):ValueExpression {
    if (!questionsExpression) {
      questionsExpression = ValueExpressionFactory.createFromFunction(function ():Array {
        return getDuplicates(config);
      });
    }
    return questionsExpression;
  }

  protected function getDuplicates(config:*):Array {
    var result:Array = [];
    var list:Array = config.bindTo.extendBy(config.propertyName).getValue();
    if (list) {
      for each(var entry:Object in list) {
        var check:DuplicateCheck = new DuplicateCheck(entry);
        if(check.getDuplicationScore() === 0) {
          continue;
        }

        result.push(ValueExpressionFactory.createFromValue(check));
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

  protected function openOverview():void {
    var url:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_button_briefing_url');
    url = StringUtil.format(url, briefingExpression.extendBy(SearchmetricsPropertyNames.ID).getValue());
    window.open(url, '_blank');
  }

  protected function getStatusText(config:DuplicatesList):String {
    var severe:Array = getSevereDuplicate(config);
    if (severe.length > 0) {
      var msg:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_duplicate_warning');
      return StringUtil.format(msg, severe.length);
    }
    return resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_duplicate_ok');
  }

  protected function getStatusError(config:DuplicatesList):Boolean {
    var severe:Array = getSevereDuplicate(config);
    return severe.length > 0;
  }

  private function getSevereDuplicate(config:DuplicatesList):Array {
    var result:Array = [];
    var list:Array = getDuplicates(config);
    if (list) {
      for each(var entry:ValueExpression in list) {
        var check:DuplicateCheck = entry.getValue();
        if (check.getDuplicationScore() >= DUPLICATE_THRESHOLD) {
          result.push(check);
        }
      }
    }

    result = result.sort(function (a:DuplicateCheck, b:DuplicateCheck):int {
      var bValue:Number = b.getDuplicationScore();
      var aValue:Number = a.getDuplicationScore();
      return aValue - bValue;
    });

    return result;
  }

  protected function visibilityTransformer(values:Array):Boolean {
    return values.length > 0;
  }
}
}
