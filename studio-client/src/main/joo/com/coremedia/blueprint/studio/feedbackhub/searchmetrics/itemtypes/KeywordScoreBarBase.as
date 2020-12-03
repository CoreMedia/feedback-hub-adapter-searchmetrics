package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.itemtypes {
import com.coremedia.cms.studio.feedbackhub.util.FeedbackHelper;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.skins.DisplayFieldSkin;

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

  protected function getIconSkin(config:KeywordScoreBar):String {
    var score:Number = config.bindTo.extendBy(config.valuePropertyName).getValue();
    var target:Number= config.bindTo.extendBy(config.targetValuePropertyName).getValue();
    if(score >= target) {
      return DisplayFieldSkin.GREEN.getSkin();
    }

    return DisplayFieldSkin.DEFAULT.getSkin();
  }

  override protected function afterRender():void {
    super.afterRender();

    var barHeight:Number = 8;
    var score:Number = bindTo.extendBy(valuePropertyName).getValue();
    score = score * 100 / bindTo.extendBy(targetValuePropertyName).getValue();
    var color:String = '#4F4F4F';
    if(score >= 100) {
      score = 100;
      color = '#5ca03f';
    }

    var field:DisplayField = queryById(BAR_ITEM_ID) as DisplayField;
    field.setValue('<div style="width: 100%;text-align: center;">' +
            '<div style="height:' + barHeight + 'px;background-color:#dcdbdb;width: 100%;"></div>' +
            '<div style="height:' + barHeight + 'px;margin-top:-' + barHeight + 'px;background-color:' +
            color + ';width: ' + FeedbackHelper.formatScore(score, 0) + '%;"></div>' +
            '</div>');
  }

  protected function formatScore(score:Number):String {
    return parseFloat('' + score).toFixed(0);
  }
}
}
