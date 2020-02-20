package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.ScoreUtil;
import com.coremedia.ui.data.ValueExpression;

import ext.container.Container;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics')]
public class ScoreCircleBase extends Container {

  public static const SCORE_ITEM_ID:String = "scoreItem";

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var propertyName:String;

  [Bindable]
  public var label:String;

  [Bindable]
  public var toPercentage:Boolean;

  [Bindable]
  public var targetScoreExpression:ValueExpression;

  public function ScoreCircleBase(config:ScoreCircle = null) {
    super(config);
  }

  override protected function afterRender():void {
    super.afterRender();
    renderScore();
  }

  private function renderScore():void {
    var el:* = queryById(SCORE_ITEM_ID).el;
    var score:Number = bindTo.extendBy(propertyName).getValue();
    if (score === undefined) {
      score = 0;
    }

    if (toPercentage) {
      score = score * 100;
    }

    var options:Object = {
      percent: el.getAttribute('data-percent') || ScoreUtil.formatScore(score),
      size: el.getAttribute('data-size') || 170,
      lineWidth: el.getAttribute('data-line') || 12,
      rotate: el.getAttribute('data-rotate') || 0
    };

    var canvas:* = window.document.createElement('canvas');
    canvas.setAttribute("style", "top:0;left:0;margin-left: -85px;");
    canvas.setAttribute("id", "score-" + this.getItemId());

    var div:* = window.document.createElement('div');
    div.setAttribute('style', 'width: 100%;text-align: center;padding-left:85px;');

    var ctx:* = canvas.getContext('2d');
    canvas.width = canvas.height = options.size;

    div.appendChild(canvas);
    el.appendChild(div);

    ctx.translate(options.size / 2, options.size / 2); // change center
    ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI); // rotate -90 deg

    var radius = (options.size - options.lineWidth) / 2;
    var drawCircle = function (color, lineWidth, percent) {
      percent = Math.min(Math.max(0, percent || 1), 1);
      ctx.beginPath();
      ctx.arc(0, 0, radius, 0, Math.PI * 2 * percent, false);
      ctx.strokeStyle = color;
      ctx.lineCap = 'butt';
      ctx.lineWidth = lineWidth;
      ctx.stroke();
    };

    drawCircle('#efefef', options.lineWidth, 100 / 100);
    if(parseInt(""+score) > 0) {
      var colorScore = score;
      if(targetScoreExpression) {
        colorScore = score * 100 / targetScoreExpression.getValue();
      }
      drawCircle(ScoreUtil.getColor(colorScore), options.lineWidth, options.percent / 100);
    }

    if(targetScoreExpression && targetScoreExpression.getValue()) {
      var targetValue:Number = targetScoreExpression.getValue();
      ctx.strokeStyle = '#c8c6c6';
      ctx.lineWidth = 3;
      ctx.lineCap = 'butt';
      ctx.beginPath();

      var delta = Math.PI * 2 * targetValue / 100;
      var startX = (radius-10)*Math.cos(delta);
      var startY = (radius-10)*Math.sin(delta);

      var endX = (radius+6)*Math.cos(delta);
      var endY = (radius+6)*Math.sin(delta);
      ctx.moveTo(startX, startY);
      ctx.lineTo(endX, endY);
      ctx.stroke();
    }
  }

  protected function getScore(config:ScoreCircle):String {
    var score:Number = config.bindTo.extendBy(config.propertyName).getValue();
    if (score === undefined) {
      score = 0;
    }

    if (config.toPercentage) {
      score = score * 100;
    }
    return ScoreUtil.formatScore(score);
  }
}
}
