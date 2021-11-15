import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import { bind } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import QuestionsListItemPanelBase from "./QuestionsListItemPanelBase";

interface QuestionsListItemPanelConfig extends Config<QuestionsListItemPanelBase> {
}

class QuestionsListItemPanel extends QuestionsListItemPanelBase {
  declare Config: QuestionsListItemPanelConfig;

  static override readonly xtype: string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.questionsListItemPanel";

  constructor(config: Config<QuestionsListItemPanel> = null) {
    super((()=> ConfigUtils.apply(Config(QuestionsListItemPanel, {
      items: [
        Config(Container, {
          items: [
            Config(DisplayField, {
              ui: ConfigUtils.asString(null),
              style: "color:#b3b1b1;",
              plugins: [
                Config(BindPropertyPlugin, {
                  componentProperty: "value",
                  bindTo: ValueExpressionFactory.createFromValue(this.getQuestions(config)),
                  transformer: bind(this, this.questionCounterTransformer),
                }),
              ],
            }),
          ],
          layout: Config(HBoxLayout, {
            align: "stretch",
            pack: "end",
          }),
        }),
        Config(Container, {
          itemId: "question-list",
          items: [
          ],
          layout: Config(VBoxLayout, { align: "stretch" }),
        }),
      ],
      layout: Config(VBoxLayout, {
        align: "stretch",
        pack: "start",
      }),
    }), config))());
  }
}

export default QuestionsListItemPanel;
