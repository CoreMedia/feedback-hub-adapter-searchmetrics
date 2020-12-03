package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.itemtypes {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.SearchmetricsPropertyNames;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.KeywordScore;
import com.coremedia.cms.studio.feedbackhub.components.itempanels.FeedbackItemPanel;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.StringUtil;

public class KeywordListItemPanelBase extends FeedbackItemPanel {
  public static const VIEW_LIMIT:int = 9;

  private var keywordScoringsExpression:ValueExpression;
  private var loadedMoreExpression:ValueExpression;

  public function KeywordListItemPanelBase(config:KeywordListItemPanel = null) {
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

  protected function getButtonLabel(config:KeywordListItemPanel):String {
    var label:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_load_more');
    var count:int = getKeywords(config).length;
    var more:int = count - VIEW_LIMIT;
    return StringUtil.format(label, more);
  }

  protected function getKeywordsExpression(config:KeywordListItemPanel):ValueExpression {
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

  protected function getButtonVisibilityExpression(config:KeywordListItemPanel):ValueExpression {
    return ValueExpressionFactory.createFromFunction(function():Boolean {
      return getKeywords(config).length > VIEW_LIMIT && !getLoadedMoreExpression().getValue();
    });
  }

  protected function getKeywords(config:KeywordListItemPanel):Array {
    var result:Array = [];
    var contentOptResults:Array = config.feedbackItem[SearchmetricsPropertyNames.CONTENT_OPT_RESULTS];
    if (contentOptResults) {
      for each(var optResult:Object in contentOptResults) {
        if (optResult.keyword === "all_topics") {
          var scores:Array = optResult[config.feedbackItem['keywordType']];
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
