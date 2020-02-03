package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.ScoreUtil;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.container.Container;
import ext.form.field.DisplayField;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics')]
public class KeywordScoreBarBase extends Container {
  public const BAR_ITEM_ID:String = "barLabel";

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var labelPropertyName:String;

  [Bindable]
  public var valuePropertyName:String;

  [Bindable]
  public var targetValuePropertyName:String;

  public function KeywordScoreBarBase(config:KeywordScoreBar = null) {
    super(config);
  }

  protected function getDisabledExpression(config:KeywordScoreBar):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():Boolean {
      var score:Number = config.bindTo.extendBy(config.valuePropertyName).getValue();
      score = score * 100 / config.bindTo.extendBy(config.targetValuePropertyName).getValue();
      return score <= 0;
    });
  }

  override protected function afterRender():void {
    super.afterRender();

    var barHeight:Number = 8;
    var score:Number = bindTo.extendBy(valuePropertyName).getValue();
    score = score * 100 / bindTo.extendBy(targetValuePropertyName).getValue();
    if(score > 100) {
      score = 100;
    }

    var field:DisplayField = queryById(BAR_ITEM_ID) as DisplayField;
    field.setValue('<div style="width: 100%;text-align: center;">' +
            '<div style="height:' + barHeight + 'px;background-color:#dcdbdb;width: 100%;"></div>' +
            '<div style="height:' + barHeight + 'px;margin-top:-' + barHeight + 'px;background-color:' +
            ScoreUtil.getColor(score) + ';width: ' + ScoreUtil.formatScore(score) + '%;"></div>' +
            '</div>');
  }

  protected function formatScore(score:Number):String {
    return parseFloat('' + score).toFixed(0);
  }
}
}
