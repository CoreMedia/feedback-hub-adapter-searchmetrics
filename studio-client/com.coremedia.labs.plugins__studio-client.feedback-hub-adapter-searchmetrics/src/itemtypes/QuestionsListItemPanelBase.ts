import Config from "@jangaroo/runtime/Config";
import { as, asConfig } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import Briefing from "../model/Briefing";
import Question from "../model/Question";
import QuestionPanel from "./QuestionPanel";
import QuestionsListItemPanel from "./QuestionsListItemPanel";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import ComponentManager from "@jangaroo/ext-ts/ComponentManager";
import StringUtil from "@jangaroo/ext-ts/String";
import Container from "@jangaroo/ext-ts/container/Container";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface QuestionsListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}



class QuestionsListItemPanelBase extends FeedbackItemPanel {
  declare Config: QuestionsListItemPanelBaseConfig;

  constructor(config:Config<QuestionsListItemPanel> = null) {
    super(config);
  }

  protected override afterRender():void {
    super.afterRender();

    var briefing = new Briefing(this.feedbackItem["briefing"]);

    var parent =as( this.queryById("question-list"),  Container);
    var questions = this.getQuestions(this.initialConfig);
    var lastGroup:string = null;
    for(var q of questions as ValueExpression[]) {
      var baseConfig:Record<string,any> = {
        bindTo: q,
        xtype: QuestionPanel.xtype,
        briefing: briefing
      };

      if (lastGroup === null) {
        lastGroup = this.#getGroupName(q.getValue());
      }
      else if (lastGroup !== this.#getGroupName(q.getValue())) {
        lastGroup = this.#getGroupName(q.getValue());

        //TODO we have no skin for these kinf of spacers
        var spacerCfg:Record<string,any> = {
          style: "border-top: solid 1px #b3b1b1;margin-top:12px;",
          height: 20,
          xtype: "container"
        };

        var spacer =as( ComponentManager.create(spacerCfg),  Container);
        parent.add(spacer);
      }

      var questionPanel =as( ComponentManager.create(baseConfig),  QuestionPanel);
      parent.add(questionPanel);
    }
  }

  #getGroupName(q:Question):string {
    var name = q.getGroup();
    if (name === null || name === undefined || name.length === 0) {
      var briefing = new Briefing(this.feedbackItem["briefing"]);
      name = FeedbackHubSearchmetrics_properties["searchmetrics_other_questions_" + briefing.getLanguage()];
      if (!name) {
        name = FeedbackHubSearchmetrics_properties.searchmetrics_other_questions;
      }
    }
    return name;
  }

  protected getQuestions(config:any):Array<any> {
    var result = [];
    var questions:Array<any> = config.feedbackItem["briefing"]["questions"];
    if (questions) {
      for(var question of questions) {
        var data:Array<any> = question.data;
        if (data) {
          for(var q of data) {
            result.push(ValueExpressionFactory.createFromValue(new Question(q)));
          }
        }
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
}
export default QuestionsListItemPanelBase;
