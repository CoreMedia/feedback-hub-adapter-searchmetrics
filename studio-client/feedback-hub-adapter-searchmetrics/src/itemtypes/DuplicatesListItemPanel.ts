import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import IconDisplayField from "@coremedia/studio-client.ext.ui-components/components/IconDisplayField";
import BindComponentsPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindComponentsPlugin";
import BindVisibilityPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindVisibilityPlugin";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackHubPropertyNames from "@coremedia/studio-client.main.feedback-hub-editor-components/util/FeedbackHubPropertyNames";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import { bind } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import DuplicatePanel from "./DuplicatePanel";
import DuplicatesListItemPanelBase from "./DuplicatesListItemPanelBase";
import StatusLabel from "./StatusLabel";

interface DuplicatesListItemPanelConfig extends Config<DuplicatesListItemPanelBase> {
}

class DuplicatesListItemPanel extends DuplicatesListItemPanelBase {
  declare Config: DuplicatesListItemPanelConfig;

  static override readonly xtype: string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.duplicatesListItemPanel";

  constructor(config: Config<DuplicatesListItemPanel> = null) {
    super((()=> ConfigUtils.apply(Config(DuplicatesListItemPanel, {
      items: [
        Config(Container, {
          items: [
            Config(IconDisplayField, {
              tooltip: this.getLabel(config.feedbackItem[FeedbackHubPropertyNames.PROPERTY_HELP]),
              iconCls: CoreIcons_properties.help,
              plugins: [
                Config(BindVisibilityPlugin, { bindTo: ValueExpressionFactory.createFromValue(this.getLabel(config.feedbackItem[FeedbackHubPropertyNames.PROPERTY_HELP])) }),
              ],
            }),
          ],
          layout: Config(HBoxLayout, {
            align: "stretch",
            pack: "start",
          }),
        }),
        Config(StatusLabel, {
          flex: 1,
          text: this.getStatusText(config),
          error: this.getStatusError(config),
        }),
        Config(Container, {
          items: [
            Config(DisplayField, {
              ui: DisplayFieldSkin.BOLD.getSkin(),
              value: FeedbackHubSearchmetrics_properties.searchmetrics_duplicated_page + ":",
            }),
            Config(Container, { flex: 1 }),
            Config(Container, {
              width: 80,
              items: [
                Config(DisplayField, {
                  ui: ConfigUtils.asString(null),
                  value: FeedbackHubSearchmetrics_properties.searchmetrics_similarity,
                  style: "color:#b3b1b1;",
                }),
              ],
              layout: Config(HBoxLayout, {
                align: "stretch",
                pack: "center",
              }),
            }),
          ],
          plugins: [
            Config(BindVisibilityPlugin, {
              bindTo: this.getDuplicatesExpression(config),
              transformer: bind(this, this.visibilityTransformer),
            }),
          ],
          layout: Config(HBoxLayout, { align: "stretch" }),
        }),
        Config(Container, {
          items: [
          ],
          layout: Config(VBoxLayout, { align: "stretch" }),
          plugins: [
            Config(BindComponentsPlugin, {
              valueExpression: this.getDuplicatesExpression(config),
              getKey: (value: ValueExpression): string => value.getValue().getUrl(),
              configBeanParameterName: "bindTo",
              reuseComponents: false,
              template: Config(DuplicatePanel),
            }),
          ],
        }),
        Config(Container, { height: 12 }),
        Config(Container, {
          items: [
            Config(Button, {
              ui: ButtonSkin.SIMPLE_PRIMARY.getSkin(),
              handler: bind(this, this.openOverview),
              text: FeedbackHubSearchmetrics_properties.searchmetrics_show_analyzed_pages,
            }),
          ],
          layout: Config(HBoxLayout, {
            align: "stretch",
            pack: "start",
          }),
        }),
      ],
      layout: Config(VBoxLayout, { align: "stretch" }),
    }), config))());
  }
}

export default DuplicatesListItemPanel;
