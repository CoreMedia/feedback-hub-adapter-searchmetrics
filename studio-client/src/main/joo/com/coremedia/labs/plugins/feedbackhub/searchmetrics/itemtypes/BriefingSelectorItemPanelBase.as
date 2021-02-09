package com.coremedia.labs.plugins.feedbackhub.searchmetrics.itemtypes {
import com.coremedia.cap.common.JobExecutionError;
import com.coremedia.cap.common.impl.GenericRemoteJob;
import com.coremedia.cap.common.jobService;
import com.coremedia.cap.content.Content;
import com.coremedia.cms.editor.sdk.editorContext;
import com.coremedia.cms.studio.feedbackhub.components.itempanels.FeedbackItemPanel;
import com.coremedia.labs.plugins.feedbackhub.searchmetrics.model.Briefing;
import com.coremedia.labs.plugins.feedbackhub.searchmetrics.model.BriefingInfo;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;
import com.coremedia.ui.messagebox.MessageBoxUtil;
import com.coremedia.ui.skins.LoadMaskSkin;

import ext.LoadMask;
import ext.button.Button;

[ResourceBundle('com.coremedia.cms.studio.feedbackhub.FeedbackHub')]
public class BriefingSelectorItemPanelBase extends FeedbackItemPanel {
  protected static const BRIEFING_COMBOBOX_ITEM_ID:String = "briefingCombo";

  private var briefingsExpression:ValueExpression;
  private var briefingInfoExpression:ValueExpression;
  private var briefingContentExpression:ValueExpression;

  private var loadMask:LoadMask;

  public function BriefingSelectorItemPanelBase(config:BriefingSelectorItemPanel = null) {
    super(config);
    addListener('afterlayout', checkBriefing);
  }

  private function checkBriefing():void {
    removeListener('afterlayout', checkBriefing);
    var briefingId:String = feedbackItem['briefingId'];
    if(briefingId) {
      getFeedbackTabPanel().setActiveTab(1);
    }
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
      var content:Content = contentExpression.getValue();
      var siteId:String = editorContext.getSitesService().getSiteIdFor(content);
      if (!siteId) {
        siteId = "all"
      }

      var params:Object = {
        briefingId: info.getBriefingId(),
        siteId: siteId,
        groupId: feedbackGroup.name
      };

      var JOB_TYPE:String = "getBriefingDetails";
      jobService.executeJob(
              new GenericRemoteJob(JOB_TYPE, params),
              //on success
              function (details:Object):void {
                var result:Object = details;
                var briefing:Briefing = new Briefing(result);
                getBriefingContentExpression().setValue(briefing.getContent());
              },
              //on error
              function (error:JobExecutionError):void {
                trace('[ERROR]', "Error loading briefing details: " + error);
              }
      );
    }
  }

  internal function getBriefingContentExpression():ValueExpression {
    if (!briefingContentExpression) {
      briefingContentExpression = ValueExpressionFactory.createFromValue(undefined);
    }
    return briefingContentExpression;
  }

  internal function reloadBriefings(b:Button):void {
    var btn:Button = b;
    btn.setDisabled(true);
    var content:Content = contentExpression.getValue();
    var siteId:String = editorContext.getSitesService().getSiteIdFor(content);
    if (!siteId) {
      siteId = "all"
    }

    var JOB_TYPE:String = "getBriefings";
    jobService.executeJob(
            new GenericRemoteJob(JOB_TYPE, {
              siteId: siteId,
              groupId: feedbackGroup.name
            }),
            //on success
            function (briefings:Object):void {
              if (loadMask && !loadMask.destroyed) {
                loadMask.destroy();
              }
              btn.setDisabled(false);
              var infos:Array = briefings as Array;
              var result:Array = [];
              if (infos !== undefined) {
                for each(var b:Object in infos) {
                  var briefingInfo:BriefingInfo = new BriefingInfo(b);
                  result.push(briefingInfo);
                }
              }
              getBriefingsExpression().setValue(result);
            },
            //on error
            function (error:JobExecutionError):void {
              if (loadMask && !loadMask.destroyed) {
                loadMask.destroy();
              }
              btn.setDisabled(false);
              trace('[ERROR]', "Error loading briefings: " + error);
            }
    );

    if(!loadMask || loadMask.destroyed) {
      var loadMaskConfig:LoadMask = LoadMask({});
      loadMaskConfig.ui = LoadMaskSkin.TRANSPARENT.getSkin();
      loadMaskConfig.msg = '';
      loadMaskConfig.target = btn;
      loadMask = new LoadMask(loadMaskConfig);
    }

    loadMask.show();
  }

  internal function applyBriefingSelection(b:Button):void {
    var info:BriefingInfo = getBriefingInfoExpression().getValue() as BriefingInfo;

    var title:String = resourceManager.getString('com.coremedia.labs.plugins.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_briefing_apply_title');
    var msg:String = resourceManager.getString('com.coremedia.labs.plugins.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_briefing_apply_text');
    MessageBoxUtil.showConfirmation(title, msg, resourceManager.getString('com.coremedia.labs.plugins.feedbackhub.searchmetrics.FeedbackHubSearchmetrics', 'searchmetrics_briefing_apply_button'),
            function (btn:*):void {
              if (btn === 'ok') {
                if (info) {
                  b.setDisabled(true);
                  queryById(BRIEFING_COMBOBOX_ITEM_ID).setDisabled(true);

                  var content:Content = contentExpression.getValue();
                  var siteId:String = editorContext.getSitesService().getSiteIdFor(content);
                  if (!siteId) {
                    siteId = "all"
                  }

                  var params:Object = {
                    briefingId: info.getBriefingId(),
                    contentId: contentExpression.getValue().getId(),
                    siteId: siteId,
                    groupId: feedbackGroup.name
                  };

                  var JOB_TYPE:String = "assignBriefing";
                  jobService.executeJob(
                          new GenericRemoteJob(JOB_TYPE, params),
                          //on success
                          function (details:Object):void {
                            reload();
                          },
                          //on error
                          function (error:JobExecutionError):void {
                            trace('[ERROR]', "Error assigning briefing: " + error);
                          }
                  );
                }
              }
            });
  }
}
}
