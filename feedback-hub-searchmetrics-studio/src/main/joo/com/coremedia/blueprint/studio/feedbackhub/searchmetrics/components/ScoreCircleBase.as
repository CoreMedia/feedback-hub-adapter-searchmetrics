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

    var div:* = window.document.createElement('div');
    div.setAttribute('style', 'width: 100%;text-align: center;padding-left:85px;');

    var span1:* = window.document.createElement('span');
    span1.setAttribute("style", " color:black;display:inline;font-family:sans-serif;position:absolute;margin-left: -66px;top:100px;font-size:44px;font-weight:bold;white-space: pre-wrap;");

    var percentValue:String = "" + options.percent;
    while (percentValue.length < 3) {
      percentValue = "  " + percentValue;
    }

    span1.textContent = percentValue;

    var span2:* = window.document.createElement('span');
    span2.setAttribute("style", " color:#b1b1b1;display:inline;font-family:sans-serif;position:absolute; top: 106px;margin-left:12px;font-size:24px;");
    span2.textContent = '/100';

    var ctx:* = canvas.getContext('2d');
    canvas.width = canvas.height = options.size;

    div.appendChild(span1);
    div.appendChild(span2);
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
    drawCircle(ScoreUtil.getColor(score), options.lineWidth, options.percent / 100);
  }
}
}
