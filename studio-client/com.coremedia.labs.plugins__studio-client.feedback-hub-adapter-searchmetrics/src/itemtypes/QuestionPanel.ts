import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";
import QuestionPanelBase from "./QuestionPanelBase";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
interface QuestionPanelConfig extends Config<QuestionPanelBase> {
}



    class QuestionPanel extends QuestionPanelBase{
  declare Config: QuestionPanelConfig;

  static override readonly xtype:string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.questionPanel";

  readonly #QUESTION_STYLE:string = "font-size: 14px;padding-top: 4px;color: #006CAE;font-weight: bold;text-transform: uppercase;";

  constructor(config:Config<QuestionPanel> = null){
    super((()=> ConfigUtils.apply(Config(QuestionPanel, {

  items:[
    Config(DisplayField, { width: 100, ui: ConfigUtils.asString( null),
                  style: this.#QUESTION_STYLE,
      plugins:[
        Config(BindPropertyPlugin, { componentProperty: "value",
                               bindTo: config.bindTo.extendBy(SearchmetricsPropertyNames.GROUP),
                               transformer: bind(this,this.groupTransformer)})
      ]
    }),
    Config(DisplayField, { flex: 1,
      plugins:[
        Config(BindPropertyPlugin, { componentProperty: "value",
                               bindTo: config.bindTo.extendBy(SearchmetricsPropertyNames.QUESTION)})
      ]
    })
  ],
  layout: Config(HBoxLayout, { align: "stretch"
  })
}),config))());
  }}
export default QuestionPanel;
