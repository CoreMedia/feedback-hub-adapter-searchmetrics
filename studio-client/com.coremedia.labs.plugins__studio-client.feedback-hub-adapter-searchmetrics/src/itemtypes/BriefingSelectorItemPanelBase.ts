import GenericRemoteJob from "@coremedia/studio-client.cap-rest-client-impl/common/impl/GenericRemoteJob";
import JobExecutionError from "@coremedia/studio-client.cap-rest-client/common/JobExecutionError";
import jobService from "@coremedia/studio-client.cap-rest-client/common/jobService";
import Content from "@coremedia/studio-client.cap-rest-client/content/Content";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import MessageBoxUtil from "@coremedia/studio-client.ext.ui-components/messagebox/MessageBoxUtil";
import LoadMaskSkin from "@coremedia/studio-client.ext.ui-components/skins/LoadMaskSkin";
import editorContext from "@coremedia/studio-client.main.editor-components/sdk/editorContext";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import LoadMask from "@jangaroo/ext-ts/LoadMask";
import Button from "@jangaroo/ext-ts/button/Button";
import { as, bind } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import trace from "@jangaroo/runtime/trace";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import Briefing from "../model/Briefing";
import BriefingInfo from "../model/BriefingInfo";
import BriefingSelectorItemPanel from "./BriefingSelectorItemPanel";

interface BriefingSelectorItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}

class BriefingSelectorItemPanelBase extends FeedbackItemPanel {
  declare Config: BriefingSelectorItemPanelBaseConfig;

  protected static readonly BRIEFING_COMBOBOX_ITEM_ID: string = "briefingCombo";

  #briefingsExpression: ValueExpression = null;

  #briefingInfoExpression: ValueExpression = null;

  #briefingContentExpression: ValueExpression = null;

  #loadMask: LoadMask = null;

  constructor(config: Config<BriefingSelectorItemPanel> = null) {
    super(config);
    this.addListener("afterlayout", bind(this, this.#checkBriefing));
  }

  #checkBriefing(): void {
    this.removeListener("afterlayout", bind(this, this.#checkBriefing));
    const briefingId: string = this.feedbackItem["briefingId"];
    if (briefingId) {
      this.getFeedbackTabPanel().setActiveTab(1);
    }
  }

  protected override afterRender(): void {
    super.afterRender();
    const briefingId: string = this.feedbackItem["briefingId"];

    ValueExpressionFactory.createFromFunction((): Array<any> => {
      const result = [];
      const infos: Array<any> = this.feedbackItem["briefingInfos"];
      if (infos !== undefined) {
        for (const b of infos) {
          const briefingInfo = new BriefingInfo(b);
          result.push(briefingInfo);
        }
      }
      return result;
    }).loadValue((briefings: Array<any>): void => {
      this.getBriefingsExpression().setValue(briefings);
      for (const b of briefings as BriefingInfo[]) {
        if (briefingId === b.getBriefingId()) {
          this.getBriefingInfoExpression().setValue(b);
        }
      }
    });
  }

  getBriefingsExpression(): ValueExpression {
    if (!this.#briefingsExpression) {
      this.#briefingsExpression = ValueExpressionFactory.createFromValue([]);
    }
    return this.#briefingsExpression;
  }

  getBriefingInfoExpression(): ValueExpression {
    if (!this.#briefingInfoExpression) {
      this.#briefingInfoExpression = ValueExpressionFactory.createFromValue(null);
      this.#briefingInfoExpression.addChangeListener(bind(this, this.#briefingInfoSelectionChanged));
    }
    return this.#briefingInfoExpression;
  }

  #briefingInfoSelectionChanged(ve: ValueExpression): void {
    const info = as(ve.getValue(), BriefingInfo);
    if (info) {
      this.getBriefingContentExpression().setValue(undefined);
      const content: Content = this.contentExpression.getValue();
      let siteId = editorContext._.getSitesService().getSiteIdFor(content);
      if (!siteId) {
        siteId = "all";
      }

      const params: Record<string, any> = {
        briefingId: info.getBriefingId(),
        siteId: siteId,
        groupId: this.feedbackGroup.name,
      };

      const JOB_TYPE = "getBriefingDetails";
      jobService._.executeJob(
        new GenericRemoteJob(JOB_TYPE, params),
        //on success
        (details: any): void => {
          const result = details;
          const briefing = new Briefing(result);
          this.getBriefingContentExpression().setValue(briefing.getContent());
        },
        //on error
        (error: JobExecutionError): void =>
          trace("[ERROR]", "Error loading briefing details: " + error),

      );
    }
  }

  getBriefingContentExpression(): ValueExpression {
    if (!this.#briefingContentExpression) {
      this.#briefingContentExpression = ValueExpressionFactory.createFromValue(undefined);
    }
    return this.#briefingContentExpression;
  }

  reloadBriefings(b: Button): void {
    const btn = b;
    btn.setDisabled(true);
    const content: Content = this.contentExpression.getValue();
    let siteId = editorContext._.getSitesService().getSiteIdFor(content);
    if (!siteId) {
      siteId = "all";
    }

    const JOB_TYPE = "getBriefings";
    jobService._.executeJob(
      new GenericRemoteJob(JOB_TYPE, {
        siteId: siteId,
        groupId: this.feedbackGroup.name,
      }),
      //on success
      (briefings: any): void => {
        if (this.#loadMask && !this.#loadMask.destroyed) {
          this.#loadMask.destroy();
        }
        btn.setDisabled(false);
        const infos = as(briefings, Array);
        const result = [];
        if (infos !== undefined) {
          for (const b of infos) {
            const briefingInfo = new BriefingInfo(b);
            result.push(briefingInfo);
          }
        }
        this.getBriefingsExpression().setValue(result);
      },
      //on error
      (error: JobExecutionError): void => {
        if (this.#loadMask && !this.#loadMask.destroyed) {
          this.#loadMask.destroy();
        }
        btn.setDisabled(false);
        trace("[ERROR]", "Error loading briefings: " + error);
      },
    );

    if (!this.#loadMask || this.#loadMask.destroyed) {
      const loadMaskConfig = Config(LoadMask);
      loadMaskConfig.ui = LoadMaskSkin.TRANSPARENT.getSkin();
      loadMaskConfig.msg = "";
      loadMaskConfig.target = btn;
      this.#loadMask = new LoadMask(loadMaskConfig);
    }

    this.#loadMask.show();
  }

  applyBriefingSelection(b: Button): void {
    const info = as(this.getBriefingInfoExpression().getValue(), BriefingInfo);

    const title = FeedbackHubSearchmetrics_properties.searchmetrics_briefing_apply_title;
    const msg = FeedbackHubSearchmetrics_properties.searchmetrics_briefing_apply_text;
    MessageBoxUtil.showConfirmation(title, msg, FeedbackHubSearchmetrics_properties.searchmetrics_briefing_apply_button,
      (btn: any): void => {
        if (btn === "ok") {
          if (info) {
            b.setDisabled(true);
            this.queryById(BriefingSelectorItemPanelBase.BRIEFING_COMBOBOX_ITEM_ID).setDisabled(true);

            const content: Content = this.contentExpression.getValue();
            let siteId = editorContext._.getSitesService().getSiteIdFor(content);
            if (!siteId) {
              siteId = "all";
            }

            const params: Record<string, any> = {
              briefingId: info.getBriefingId(),
              contentId: this.contentExpression.getValue().getId(),
              siteId: siteId,
              groupId: this.feedbackGroup.name,
            };

            const JOB_TYPE = "assignBriefing";
            jobService._.executeJob(
              new GenericRemoteJob(JOB_TYPE, params),
              //on success
              (details: any): void =>
                this.reload()
              ,
              //on error
              (error: JobExecutionError): void =>
                trace("[ERROR]", "Error assigning briefing: " + error),

            );
          }
        }
      });
  }
}

export default BriefingSelectorItemPanelBase;
