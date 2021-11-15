import Config from "@jangaroo/runtime/Config";
import { asConfig } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import Briefing from "../model/Briefing";
import QuestionPanel from "./QuestionPanel";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import Container from "@jangaroo/ext-ts/container/Container";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface QuestionPanelBaseConfig extends Config<Container>, Partial<Pick<QuestionPanelBase,
  "bindTo" |
  "briefing"
>> {
}



class QuestionPanelBase extends Container {
  declare Config: QuestionPanelBaseConfig;

  #bindTo:ValueExpression = null;

  get bindTo():ValueExpression { return this.#bindTo; }
  set bindTo(value:ValueExpression) { this.#bindTo = value; }

  #briefing:Briefing = null;

  get briefing():Briefing { return this.#briefing; }
  set briefing(value:Briefing) { this.#briefing = value; }

  constructor(config:Config<QuestionPanel> = null) {
    super(config);
  }

  protected groupTransformer(name:string):string {
    if (name === null) {
      name = FeedbackHubSearchmetrics_properties["searchmetrics_other_questions_" + this.briefing.getLanguage()];
      if (!name) {
        name = FeedbackHubSearchmetrics_properties.searchmetrics_other_questions;
      }
    }
    return name;
  }
}
export default QuestionPanelBase;
