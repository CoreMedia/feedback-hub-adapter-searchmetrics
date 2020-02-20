package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.ScoreUtil;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;
import ext.container.Container;
import ext.form.field.DisplayField;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics')]
public class ScoreBarBase extends Container {

  public const BAR_ITEM_ID:String = "barLabel";
  public const BAR_HEIGHT:Number = 8;

  private var fixUnitExpression:ValueExpression;

  [Bindable]
  public var label:String;

  [Bindable]
  public var startLabel:String;

  [Bindable]
  public var endLabel:String;

  [Bindable]
  public var targetValueExpression:ValueExpression;

  [Bindable]
  public var unit:String;

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var propertyName:String;

  [Bindable]
  public var showScoreValue:Boolean;

  [Bindable]
  public var reverseScoreColor:Boolean = false;

  [Bindable]
  public var toPercentage:Boolean;

  [Bindable]
  public var maxValue:Number = -1;

  [Bindable]
  public var multiplier:Number = 1;

  [Bindable]
  public var qtip:String;

  public function ScoreBarBase(config:ScoreBar = null) {
    super(config);
  }

  protected function getUnitExpression(config:ScoreBar):ValueExpression {
    if (!fixUnitExpression) {
      fixUnitExpression = ValueExpressionFactory.createFromFunction(function ():String {
        if (config.targetValueExpression) {
          return '/' + config.targetValueExpression.getValue();
        }

        if (config.unit === null || config.unit === undefined) {
          return "/100";
        }

        if (StringUtil.trim(config.unit).length > 0) {
          return "/" + config.unit;
        }

        return "";
      });
    }
    return fixUnitExpression;
  }

  override protected function afterRender():void {
    super.afterRender();

    var score:Number = bindTo.extendBy(propertyName).getValue();
    if (toPercentage) {
      score = score * 100;
    }

    if (targetValueExpression) {
      score = score * 100 / targetValueExpression.getValue();
    }

    score = multiplier * score;

    var field:DisplayField = queryById(BAR_ITEM_ID) as DisplayField;
    if (maxValue > 0) {
      var html:String = '<table cellpadding="0" cellspacing="1" border="0" style="width: 100%;table-layout: fixed;"><tr style="height:' + BAR_HEIGHT + 'px;">';

      for (var i:int = 0; i < maxValue; i++) {
        var color:String = "#efefef";
        if (i <= score && score > 0) {
          color = ScoreUtil.getColor(score * multiplier, reverseScoreColor)
        }
        html += '<td style="background-color: ' + color + '" />';
      }
      html += '</tr><tr>';
      for (var j:int = 0; j < maxValue; j++) {
        html += '<td align="center" data-qtip="' + (j + 1) + '" style="font-size:10px;">' + (j + 1) + '</td>';
      }
      html += '</tr></table>';
      field.setValue(html);
      field.setHeight(42);
    }
    else {
      field.setValue('<div style="width: 100%;text-align: center;">' +
              '<div style="height:' + BAR_HEIGHT + 'px;background-color:#efefef;width: 100%;"></div>' +
              '<div style="height:' + BAR_HEIGHT + 'px;margin-top:-' + BAR_HEIGHT + 'px;background-color:' +
              ScoreUtil.getColor(score, reverseScoreColor) + ';width: ' + ScoreUtil.formatScore(score) + '%;"></div>' +
              '</div>');
    }
  }

  protected function formatScore(score:Number):String {
    if (score === undefined) {
      score = 0;
    }
    if (toPercentage) {
      score = score * 100;
    }
    var score:* =  parseFloat('' + score).toFixed(0);

    if(this.unit === '') {
      score = score + '%';
    }

    return score;
  }
}
}
