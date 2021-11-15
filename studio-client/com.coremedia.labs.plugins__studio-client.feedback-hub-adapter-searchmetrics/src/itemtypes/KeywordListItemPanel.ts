import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import IconDisplayField from "@coremedia/studio-client.ext.ui-components/components/IconDisplayField";
import BindComponentsPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindComponentsPlugin";
import BindVisibilityPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindVisibilityPlugin";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import { bind } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import KeywordListItemPanelBase from "./KeywordListItemPanelBase";
import KeywordScoreBar from "./KeywordScoreBar";

interface KeywordListItemPanelConfig extends Config<KeywordListItemPanelBase> {
}

class KeywordListItemPanel extends KeywordListItemPanelBase {
  declare Config: KeywordListItemPanelConfig;

  static override readonly xtype: string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.keywordListItemPanel";

  constructor(config: Config<KeywordListItemPanel> = null) {
    super((()=> ConfigUtils.apply(Config(KeywordListItemPanel, {
      items: [
        Config(Container, {
          items: [
            Config(IconDisplayField, {
              tooltip: this.getLabel(config.feedbackItem["help"]),
              iconCls: CoreIcons_properties.help,
              plugins: [
                Config(BindVisibilityPlugin, { bindTo: ValueExpressionFactory.createFromValue(this.getLabel(config.feedbackItem["help"])) }),
              ],
            }),
          ],
          layout: Config(HBoxLayout, {
            align: "stretch",
            pack: "start",
          }),
        }),
        Config(Container, {
          items: [
          ],
          layout: Config(VBoxLayout, { align: "stretch" }),
          plugins: [
            Config(BindComponentsPlugin, {
              valueExpression: this.getKeywordsExpression(config),
              getKey: (value: ValueExpression): string => value.getValue().getTermId(),
              configBeanParameterName: "bindTo",
              reuseComponents: false,
              template: Config(KeywordScoreBar, {
                labelPropertyName: "keyword",
                valuePropertyName: "current",
                targetValuePropertyName: "target",
              }),
            }),
          ],
        }),
        Config(Container, {
          items: [
            Config(Button, {
              ui: ConfigUtils.asString(ButtonSkin.SIMPLE),
              text: this.getButtonLabel(config),
              handler: bind(this, this.loadMore),
              plugins: [
                Config(BindVisibilityPlugin, { bindTo: this.getButtonVisibilityExpression(config) }),
              ],
            }),
          ],
          layout: Config(HBoxLayout, {
            align: "stretch",
            pack: "start",
          }),
        }),
      ],
      layout: Config(VBoxLayout, {
        align: "stretch",
        pack: "start",
      }),
    }), config))());
  }
}

export default KeywordListItemPanel;
