import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import ComponentManager from "@jangaroo/ext-ts/ComponentManager";
import StringUtil from "@jangaroo/ext-ts/String";
import Container from "@jangaroo/ext-ts/container/Container";
import { as } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import Briefing from "../model/Briefing";
import Question from "../model/Question";
import QuestionPanel from "./QuestionPanel";
import QuestionsListItemPanel from "./QuestionsListItemPanel";

interface QuestionsListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}

class QuestionsListItemPanelBase extends FeedbackItemPanel {
  declare Config: QuestionsListItemPanelBaseConfig;

  constructor(config: Config<QuestionsListItemPanel> = null) {
    super(config);
  }

  protected override afterRender(): void {
    super.afterRender();

    const briefing = new Briefing(this.feedbackItem["briefing"]);

    const parent = as(this.queryById("question-list"), Container);
    const questions = this.getQuestions(this.initialConfig);
    let lastGroup: string = null;
    for (const q of questions as ValueExpression[]) {
      const baseConfig: Record<string, any> = {
        bindTo: q,
        xtype: QuestionPanel.xtype,
        briefing: briefing,
      };

      if (lastGroup === null) {
        lastGroup = this.#getGroupName(q.getValue());
      } else if (lastGroup !== this.#getGroupName(q.getValue())) {
        lastGroup = this.#getGroupName(q.getValue());

        //TODO we have no skin for these kinf of spacers
        const spacerCfg: Record<string, any> = {
          style: "border-top: solid 1px #b3b1b1;margin-top:12px;",
          height: 20,
          xtype: "container",
        };

        const spacer = as(ComponentManager.create(spacerCfg), Container);
        parent.add(spacer);
      }

      const questionPanel = as(ComponentManager.create(baseConfig), QuestionPanel);
      parent.add(questionPanel);
    }
  }

  #getGroupName(q: Question): string {
    let name = q.getGroup();
    if (name === null || name === undefined || name.length === 0) {
      const briefing = new Briefing(this.feedbackItem["briefing"]);
      name = FeedbackHubSearchmetrics_properties["searchmetrics_other_questions_" + briefing.getLanguage()];
      if (!name) {
        name = FeedbackHubSearchmetrics_properties.searchmetrics_other_questions;
      }
    }
    return name;
  }

  protected getQuestions(config: any): Array<any> {
    const result = [];
    const questions: Array<any> = config.feedbackItem["briefing"]["questions"];
    if (questions) {
      for (const question of questions) {
        const data: Array<any> = question.data;
        if (data) {
          for (const q of data) {
            result.push(ValueExpressionFactory.createFromValue(new Question(q)));
          }
        }
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
}

export default QuestionsListItemPanelBase;
