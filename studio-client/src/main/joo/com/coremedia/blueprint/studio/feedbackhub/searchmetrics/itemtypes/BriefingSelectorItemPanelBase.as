package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.itemtypes {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.Briefing;
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.BriefingInfo;
import com.coremedia.cms.editor.sdk.editorContext;
import com.coremedia.cms.studio.feedbackhub.components.itempanels.*;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.data.impl.RemoteServiceMethod;
import com.coremedia.ui.data.impl.RemoteServiceMethodResponse;
import com.coremedia.ui.messagebox.MessageBoxUtil;

import ext.button.Button;

[ResourceBundle('com.coremedia.cms.studio.feedbackhub.FeedbackHub')]
public class BriefingSelectorItemPanelBase extends FeedbackItemPanel {
  protected static const BRIEFING_COMBOBOX_ITEM_ID:String = "briefingCombo";

  private var briefingsExpression:ValueExpression;
  private var briefingInfoExpression:ValueExpression;
  private var briefingContentExpression:ValueExpression;

  private static var lastBriefingInfo:BriefingInfo = null;

  public function BriefingSelectorItemPanelBase(config:BriefingSelectorItemPanel = null) {
    super(config);
  }

  override protected function afterRender():void {
    super.afterRender();
    var briefingId:String = feedbackItem['briefingId'];

    ValueExpressionFactory.createFromFunction(function ():Array {
      var result:Array = [];
      var infos:Array = feedbackItem['briefingInfos'];
      if (infos !== undefined) {
        for each(var b:Object in infos) {
          var briefingInfo:BriefingInfo = new BriefingInfo(b);
          result.push(briefingInfo);
        }
      }
      return result;
    }).loadValue(function (briefings:Array):void {
      getBriefingsExpression().setValue(briefings);
      for each(var b:BriefingInfo in briefings) {
        if (briefingId === b.getBriefingId()) {
          getBriefingInfoExpression().setValue(b);
        }
      }
    });
  }

  internal function getBriefingsExpression():ValueExpression {
    if (!briefingsExpression) {
      briefingsExpression = ValueExpressionFactory.createFromValue([]);
    }
    return briefingsExpression;
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
        contentId: contentExpression.getValue().getId()
      };

      var siteId:String = editorContext.getSitesService().getPreferredSiteId();
      if(!siteId) {
        siteId = "all"
      }
      var remoteServiceMethod:RemoteServiceMethod = new RemoteServiceMethod('searchmetrics/' + siteId + '/briefing/' + info.getBriefingId(), 'GET');
      remoteServiceMethod.request(params, function (response:RemoteServiceMethodResponse):void {
        if (response.success) {
          var result:Object = response.getResponseJSON();
          var briefing:Briefing = new Briefing(response.getResponseJSON());
          getBriefingContentExpression().setValue(briefing.getContent());
        }
      });
    }
  }

  internal function getBriefingContentExpression():ValueExpression {
    if (!briefingContentExpression) {
      briefingContentExpression = ValueExpressionFactory.createFromValue(undefined);
      briefingContentExpression.addChangeListener(function ():void {
        window.setTimeout(function ():void { //dunno
          getFeedbackItemsPanel().updateLayout();
        }, 100);
      });
    }
    return briefingContentExpression;
  }

  internal function reloadBriefings(b:Button):void {
    var btn:Button = b;
    btn.setDisabled(true);
    var siteId:String = editorContext.getSitesService().getPreferredSiteId();
    if(!siteId) {
      siteId = "all"
    }
    var remoteServiceMethod:RemoteServiceMethod = new RemoteServiceMethod('searchmetrics/' + siteId + '/briefings/refresh', 'GET');
    remoteServiceMethod.request(null, function (response:RemoteServiceMethodResponse):void {
      if (response.success) {
        var infos:Array = response.getResponseJSON().items;
        var result:Array = [];
        if (infos !== undefined) {
          for each(var b:Object in infos) {
            var briefingInfo:BriefingInfo = new BriefingInfo(b);
            result.push(briefingInfo);
          }
        }
        getBriefingsExpression().setValue(result);
        btn.setDisabled(false);
      }
    }, function (response:RemoteServiceMethodResponse):void {
      btn.setDisabled(false);
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
                    contentId: contentExpression.getValue().getId()
                  };

                  var siteId:String = editorContext.getSitesService().getPreferredSiteId();
                  if(!siteId) {
                    siteId = "all"
                  }
                  var remoteServiceMethod:RemoteServiceMethod = new RemoteServiceMethod('searchmetrics/' + siteId + '/briefingmapping', 'POST');
                  remoteServiceMethod.request(params, function (response:RemoteServiceMethodResponse):void {
                    if (response.success) {
                      reload();
                    }
                  });
                }
              }
            });
  }
}
}
