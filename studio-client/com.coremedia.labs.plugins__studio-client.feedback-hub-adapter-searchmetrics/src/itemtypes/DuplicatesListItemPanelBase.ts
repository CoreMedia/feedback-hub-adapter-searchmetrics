import Config from "@jangaroo/runtime/Config";
import { asConfig } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import DuplicateCheck from "../model/DuplicateCheck";
import DuplicatesListItemPanel from "./DuplicatesListItemPanel";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import StringUtil from "@jangaroo/ext-ts/String";
import int from "@jangaroo/runtime/int";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface DuplicatesListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}



class DuplicatesListItemPanelBase extends FeedbackItemPanel {
  declare Config: DuplicatesListItemPanelBaseConfig;
  static readonly #DUPLICATE_THRESHOLD:number = 16;

  #questionsExpression:ValueExpression = null;

  constructor(config:Config<DuplicatesListItemPanel> = null) {
    super(config);
  }

  protected getDuplicatesExpression(config:Config<DuplicatesListItemPanel>):ValueExpression {
    if (!this.#questionsExpression) {
      this.#questionsExpression = ValueExpressionFactory.createFromFunction(():Array<any> => 
         this.getDuplicates(config)
      );
    }
    return this.#questionsExpression;
  }

  protected getDuplicates(config:any):Array<any> {
    var result = [];
    var list:Array<any> = config.feedbackItem["duplicationCheckResults"];
    if (list) {
      for(var entry of list) {
        var check = new DuplicateCheck(entry);
        if(check.getDuplicationScore() === 0) {
          continue;
        }

        result.push(ValueExpressionFactory.createFromValue(check));
      }
    }
    return result;
  }

  protected questionCounterTransformer(questions:Array<any>):string {
    if (questions) {
      var msg = FeedbackHubSearchmetrics_properties.searchmetrics_questions;
      return StringUtil.format(msg, questions.length);
    }
    return "";
  }

  protected openOverview():void {
    var url = FeedbackHubSearchmetrics_properties.searchmetrics_button_briefing_url;
    url = StringUtil.format(url, this.feedbackItem["briefingId"]);
    window.open(url, "_blank");
  }

  protected getStatusText(config:Config<DuplicatesListItemPanel>):string {
    var severe = this.#getSevereDuplicate(config);
    if (severe.length > 0) {
      var msg = FeedbackHubSearchmetrics_properties.searchmetrics_duplicate_warning;
      return StringUtil.format(msg, severe.length);
    }
    return FeedbackHubSearchmetrics_properties.searchmetrics_duplicate_ok;
  }

  protected getStatusError(config:Config<DuplicatesListItemPanel>):boolean {
    var severe = this.#getSevereDuplicate(config);
    return severe.length > 0;
  }

  #getSevereDuplicate(config:Config<DuplicatesListItemPanel>):Array<any> {
    var result = [];
    var list = this.getDuplicates(config);
    if (list) {
      for(var entry of list as ValueExpression[]) {
        var check:DuplicateCheck = entry.getValue();
        if (check.getDuplicationScore() >= DuplicatesListItemPanelBase.#DUPLICATE_THRESHOLD) {
          result.push(check);
        }
      }
    }

    result = result.sort((a:DuplicateCheck, b:DuplicateCheck):int => {
      var bValue = b.getDuplicationScore();
      var aValue = a.getDuplicationScore();
      return aValue - bValue;
    });

    return result;
  }

  protected visibilityTransformer(values:Array<any>):boolean {
    return values.length > 0;
  }
}
export default DuplicatesListItemPanelBase;
