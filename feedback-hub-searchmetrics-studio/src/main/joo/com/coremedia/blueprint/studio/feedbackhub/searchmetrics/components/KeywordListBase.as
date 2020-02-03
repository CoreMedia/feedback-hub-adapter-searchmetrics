package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.components {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.KeywordScore;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;
import ext.container.Container;

public class KeywordListBase extends Container {
  public static const VIEW_LIMIT:int = 9;

  [Bindable]
  public var bindTo:ValueExpression;

  [Bindable]
  public var propertyName:String;

  [Bindable]
  public var keywordType:String;

  [Bindable]
  public var qtip:String;

  private var keywordScoringsExpression:ValueExpression;
  private var loadedMoreExpression:ValueExpression;

  public function KeywordListBase(config:KeywordList = null) {
    super(config);
  }

  protected function getLoadedMoreExpression():ValueExpression {
    if (!loadedMoreExpression) {
      loadedMoreExpression = ValueExpressionFactory.createFromValue(false);
    }
    return loadedMoreExpression;
  }

  protected function loadMore():void {
    getLoadedMoreExpression().setValue(true);
  }

  protected function getButtonLabel(config:KeywordList):String {
    var label:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_load_more');
    var count:int = getKeywords(config).length;
    var more:int = count - VIEW_LIMIT;
    return StringUtil.format(label, more);
  }

  protected function getKeywordsExpression(config:KeywordList):ValueExpression {
    if (!keywordScoringsExpression) {
      keywordScoringsExpression = ValueExpressionFactory.createFromFunction(function ():Array {
        var result:Array = getKeywords(config);
        if (result.length > VIEW_LIMIT && !getLoadedMoreExpression().getValue()) {
          result = result.splice(0, VIEW_LIMIT);
        }

        return result;
      });
    }
    return keywordScoringsExpression;
  }

  protected function getButtonVisibilityExpression(config:KeywordList):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():Boolean {
      return getKeywords(config).length > VIEW_LIMIT && !getLoadedMoreExpression().getValue();
    });
  }

  protected function getKeywords(config:KeywordList):Array {
    var result:Array = [];
    var contentOptResults:Array = config.bindTo.extendBy(config.propertyName).getValue();
    if (contentOptResults) {
      for each(var optResult:Object in contentOptResults) {
        if (optResult.keyword === "all_topics") {
          var scores:Array = optResult[config.keywordType];
          for each(var s:Object in scores) {
            result.push(ValueExpressionFactory.createFromValue(new KeywordScore(s)));
          }
        }
      }
    }
    return result;
  }
}
}
