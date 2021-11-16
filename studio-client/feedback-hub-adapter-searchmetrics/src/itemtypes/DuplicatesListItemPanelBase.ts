import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import StringUtil from "@jangaroo/ext-ts/String";
import Config from "@jangaroo/runtime/Config";
import int from "@jangaroo/runtime/int";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import DuplicateCheck from "../model/DuplicateCheck";
import DuplicatesListItemPanel from "./DuplicatesListItemPanel";

interface DuplicatesListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}

class DuplicatesListItemPanelBase extends FeedbackItemPanel {
  declare Config: DuplicatesListItemPanelBaseConfig;

  static readonly #DUPLICATE_THRESHOLD: number = 16;

  #questionsExpression: ValueExpression = null;

  constructor(config: Config<DuplicatesListItemPanel> = null) {
    super(config);
  }

  protected getDuplicatesExpression(config: Config<DuplicatesListItemPanel>): ValueExpression {
    if (!this.#questionsExpression) {
      this.#questionsExpression = ValueExpressionFactory.createFromFunction((): Array<any> =>
        this.getDuplicates(config),
      );
    }
    return this.#questionsExpression;
  }

  protected getDuplicates(config: any): Array<any> {
    const result = [];
    const list: Array<any> = config.feedbackItem["duplicationCheckResults"];
    if (list) {
      for (const entry of list) {
        const check = new DuplicateCheck(entry);
        if (check.getDuplicationScore() === 0) {
          continue;
        }

        result.push(ValueExpressionFactory.createFromValue(check));
      }
    }
    return result;
  }

  protected questionCounterTransformer(questions: Array<any>): string {
    if (questions) {
      const msg = FeedbackHubSearchmetrics_properties.searchmetrics_questions;
      return StringUtil.format(msg, questions.length);
    }
    return "";
  }

  protected openOverview(): void {
    let url = FeedbackHubSearchmetrics_properties.searchmetrics_button_briefing_url;
    url = StringUtil.format(url, this.feedbackItem["briefingId"]);
    window.open(url, "_blank");
  }

  protected getStatusText(config: Config<DuplicatesListItemPanel>): string {
    const severe = this.#getSevereDuplicate(config);
    if (severe.length > 0) {
      const msg = FeedbackHubSearchmetrics_properties.searchmetrics_duplicate_warning;
      return StringUtil.format(msg, severe.length);
    }
    return FeedbackHubSearchmetrics_properties.searchmetrics_duplicate_ok;
  }

  protected getStatusError(config: Config<DuplicatesListItemPanel>): boolean {
    const severe = this.#getSevereDuplicate(config);
    return severe.length > 0;
  }

  #getSevereDuplicate(config: Config<DuplicatesListItemPanel>): Array<any> {
    let result = [];
    const list = this.getDuplicates(config);
    if (list) {
      for (const entry of list as ValueExpression[]) {
        const check: DuplicateCheck = entry.getValue();
        if (check.getDuplicationScore() >= DuplicatesListItemPanelBase.#DUPLICATE_THRESHOLD) {
          result.push(check);
        }
      }
    }

    result = result.sort((a: DuplicateCheck, b: DuplicateCheck): int => {
      const bValue = b.getDuplicationScore();
      const aValue = a.getDuplicationScore();
      return aValue - bValue;
    });

    return result;
  }

  protected visibilityTransformer(values: Array<any>): boolean {
    return values.length > 0;
  }
}

export default DuplicatesListItemPanelBase;
