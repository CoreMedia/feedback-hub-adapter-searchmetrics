package com.coremedia.blueprint.studio.feedbackhub.searchmetrics {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.Briefing;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.BriefingInfo;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.ContentValidation;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.SearchmetricsFeedbackItem;
import com.coremedia.cap.content.Content;
import com.coremedia.cms.editor.sdk.util.MessageBoxUtil;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackGroupPanel;
import com.coremedia.cms.studio.feedbackhub.components.FeedbackItemPanel;
import com.coremedia.ui.data.PropertyChangeEvent;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.data.impl.RemoteServiceMethod;
import com.coremedia.ui.data.impl.RemoteServiceMethodResponse;
import com.coremedia.ui.util.EventUtil;

import ext.ObjectUtil;
import ext.StringUtil;
import ext.button.Button;
import ext.panel.Panel;
import ext.tab.TabPanel;

/**
 * The overall Searchmetrics Feedback panel.
 * Note that we implemented a lot of workaround since the lack of functionality in the Feedback Hub.
 * Check for "to-do" comments to see will likely be changed with future Feedback Hub API changes.
 */
public class SearchmetricsFeedbackItemPanelBase extends FeedbackItemPanel {
  protected static const BRIEFING_COMBOBOX_ITEM_ID:String = "briefingCombo";

  //must be static since the permanent re-creates
  private static var lastTab:Number = -1;
  private static var lastBriefingInfo:BriefingInfo = null;

  public static const TAB_PANEL:String = 'tabPanel';

  private var feedbackLoadedExpression:ValueExpression;
  private var feedbackNotLoadedExpression:ValueExpression;
  private var briefingsExpression:ValueExpression;
  private var briefingContentExpression:ValueExpression;
  private var briefingInfoExpression:ValueExpression;
  private var contentValidationExpression:ValueExpression;
  private var briefingExpression:ValueExpression;
  private var disabledExpression:ValueExpression;

  private var refreshButton:Button;
  private var content:Content;

  public function SearchmetricsFeedbackItemPanelBase(config:SearchmetricsFeedbackItemPanel = null) {
    super(config);
  }


  override protected function afterRender():void {
    super.afterRender();
    var tab:TabPanel = queryById(TAB_PANEL) as TabPanel;

    //add tab listener to store the tab state
    //TODO this is only because of the complete re-rendering of the whole panel, API should be provide static panels as well
    tab.addListener('tabchange', function (tabPanel:TabPanel, newCard:Panel):void {
      EventUtil.invokeLater(function ():void {
        tabPanel.updateLayout();//TODO collapsible children issue
      });

      lastTab = tabPanel.items.indexOf(newCard);
    });

    //TODO re-init of old UI state, only because of complete re-rerending
    var disabled:Boolean = getDisabledExpression(null).getValue();
    var validation:ContentValidation = getContentValidationExpression().getValue();
    if (disabled) {
      tab.setActiveTab(0);
    }
    else if (validation !== undefined && lastTab <= 0) {
      tab.setActiveTab(1);
    }
    else if (lastTab != -1) {
      //set the initial tab state from the static value
      tab.setActiveTab(lastTab);
    }

    //get the refresh button to listen to a refresh
    //TODO API should provide automatic refresh/function for that
    refreshButton = findParentByType(FeedbackGroupPanel.xtype).queryById('loadFeedbackButton') as Button;

    //listen on content changes
    if (SearchmetricsFeedbackItem(feedbackItem).feedback) {
      content = SearchmetricsFeedbackItem(feedbackItem).feedback.content;

      var propertyName:String = SearchmetricsFeedbackItem(feedbackItem).feedback.propertyName;
      if(!propertyName) {
        throw new Error("No property 'propertyName' set for Searchmetrics integration.");
      }
      content.getProperties().addPropertyChangeListener(propertyName, textChanged);
    }

    var info:BriefingInfo = getActiveBriefingInfo();
    if (info) {
      getBriefingInfoExpression().setValue(info);
    }
  }

  private function getActiveBriefingInfo():BriefingInfo {
    var briefing:Briefing = getBriefingValueExpression().getValue();
    if (!ObjectUtil.isEmpty(briefing)) {
      var info:BriefingInfo = new BriefingInfo(briefing.toObject());
      info.setStory(briefing.getName());
      return info;
    }

    return null;
  }

  private function textChanged(e:PropertyChangeEvent):void {
    triggerReload();
  }

  override protected function onDestroy():void {
    if(SearchmetricsFeedbackItem(feedbackItem).feedback) {
      var propertyName:String = SearchmetricsFeedbackItem(feedbackItem).feedback.propertyName;
      if (content) {
        content.getProperties().removePropertyChangeListener(propertyName, textChanged);
      }
    }

    super.onDestroy();
  }

  internal function getDisabledExpression(config:SearchmetricsFeedbackItemPanel = null):ValueExpression {
    if (!disabledExpression) {
      disabledExpression = ValueExpressionFactory.createFromFunction(function ():Boolean {
        var noFeedback:Boolean = !SearchmetricsFeedbackItem(feedbackItem).feedback || SearchmetricsFeedbackItem(feedbackItem).feedback.contentValidation === null;
        return noFeedback;
      });
    }
    return disabledExpression;
  }

  internal function getBriefingContentExpression():ValueExpression {
    if (!briefingContentExpression) {
      briefingContentExpression = ValueExpressionFactory.createFromValue(undefined);
    }
    return briefingContentExpression;
  }

  internal function getFeedbackLoadedExpression(config:SearchmetricsFeedbackItemPanel):ValueExpression {
    if (!feedbackLoadedExpression) {
      feedbackLoadedExpression = ValueExpressionFactory.createFromFunction(function ():Boolean {
        var feedback:Object = SearchmetricsFeedbackItem(config.feedbackItem).feedback;
        if (feedback && feedback.contentValidation) {
          getContentValidationExpression().setValue(new ContentValidation(feedback.contentValidation));
        }
        if (feedback && feedback.briefing) {
          getBriefingValueExpression().setValue(new Briefing(feedback.briefing));
        }
        return feedback !== undefined;
      });
    }
    return feedbackLoadedExpression;
  }

  internal function getFeedbackNotLoadedExpression(config:SearchmetricsFeedbackItemPanel):ValueExpression {
    if (!feedbackNotLoadedExpression) {
      feedbackNotLoadedExpression = ValueExpressionFactory.createFromFunction(function ():Boolean {
        return !SearchmetricsFeedbackItem(config.feedbackItem).feedback;
      });
    }
    return feedbackNotLoadedExpression;
  }

  internal function getContentValidationExpression():ValueExpression {
    if (!contentValidationExpression) {
      contentValidationExpression = ValueExpressionFactory.createFromValue({});
    }
    return contentValidationExpression;
  }

  internal function getBriefingValueExpression():ValueExpression {
    if (!briefingExpression) {
      briefingExpression = ValueExpressionFactory.createFromValue({});
    }
    return briefingExpression;
  }

  internal function getBriefingInfoExpression():ValueExpression {
    if (!briefingInfoExpression) {
      briefingInfoExpression = ValueExpressionFactory.createFromValue(null);
      briefingInfoExpression.addChangeListener(briefingInfoSelectionChanged);
    }
    return briefingInfoExpression;
  }

  private function briefingInfoSelectionChanged(ve:ValueExpression):void {
    var info:BriefingInfo = ve.getValue() as BriefingInfo;
    if (info) {
      getBriefingContentExpression().setValue(undefined);
      var params:Object = {
        briefingId: info.getBriefingId(),
        contentId: SearchmetricsFeedbackItem(feedbackItem).feedback.content.getId()
      };

      var remoteServiceMethod:RemoteServiceMethod = new RemoteServiceMethod('searchmetrics/briefing/' + info.getBriefingId(), 'GET');
      remoteServiceMethod.request(params, function (response:RemoteServiceMethodResponse):void {
        if (response.success) {
          var result:Object = response.getResponseJSON();
          var briefing:Briefing = new Briefing(response.getResponseJSON());
          getBriefingContentExpression().setValue(briefing.getContent());
          var content:String = briefing.getContent();
        }
      });
    }
  }

  /**
   * //TODO the only way to trigger the reload is to push the button progammatically. This should be changed in the API.
   */
  private function triggerReload():void {
    var content:Content = SearchmetricsFeedbackItem(feedbackItem).feedback.content;
    if (content) {
      //this fixes the validation cache key issue with using old content
      content.invalidate(function ():void {
        refreshButton.click();
      })
    }
    else {
      refreshButton.click();
    }
  }

  internal function getBriefingsExpression(config:SearchmetricsFeedbackItemPanel):ValueExpression {
    if (!briefingsExpression) {
      briefingsExpression = ValueExpressionFactory.createFromFunction(function ():Array {
        var result:Array = [];
        var data:* = SearchmetricsFeedbackItem(config.feedbackItem);
        if (data.feedback !== undefined && data.feedback.briefings !== undefined) {
          var briefings:Array = data.feedback.briefings;
          for each(var b:Object in briefings) {
            result.push(new BriefingInfo(b));
          }
        }
        return result;
      });
    }
    return briefingsExpression;
  }

  internal function reloadBriefings():void {
    var remoteServiceMethod:RemoteServiceMethod = new RemoteServiceMethod('searchmetrics/briefings/refresh', 'GET');
    remoteServiceMethod.request(null, function (response:RemoteServiceMethodResponse):void {
      if (response.success) {
        triggerReload();
      }
    });
  }

  internal function applyBriefingSelection(b:Button):void {
    var info:BriefingInfo = getBriefingInfoExpression().getValue() as BriefingInfo;
    if (lastBriefingInfo !== null && info !== null && lastBriefingInfo.getBriefingId() === info.getBriefingId()) {
      return;
    }

    var title:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_briefing_apply_title');
    var msg:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_briefing_apply_text');
    MessageBoxUtil.showConfirmation(title, msg, resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_briefing_apply_button'),
      function (btn:*):void {
        if (btn === 'ok') {
          lastBriefingInfo = info;

          if (info) {
            b.setDisabled(true);
            queryById(BRIEFING_COMBOBOX_ITEM_ID).setDisabled(true);

            var params:Object = {
              briefingId: info.getBriefingId(),
              contentId: SearchmetricsFeedbackItem(feedbackItem).feedback.content.getId()
            };

            var remoteServiceMethod:RemoteServiceMethod = new RemoteServiceMethod('searchmetrics/briefingmapping', 'POST');
            remoteServiceMethod.request(params, function (response:RemoteServiceMethodResponse):void {
              if (response.success) {
                triggerReload();
              }
            });
          }
        }
      });
  }

  internal function targetScoreTransformer(value:Number):String {
    var msg:String = resourceManager.getString('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_target_score');
    return StringUtil.format(msg, value);
  }
}
}
