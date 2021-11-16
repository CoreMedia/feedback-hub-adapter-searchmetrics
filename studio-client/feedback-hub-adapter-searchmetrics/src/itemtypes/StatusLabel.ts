import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import IconDisplayField from "@coremedia/studio-client.ext.ui-components/components/IconDisplayField";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";

interface StatusLabelConfig extends Config<Container>, Partial<Pick<StatusLabel,
  "text" |
  "error"
>> {
}

class StatusLabel extends Container {
  declare Config: StatusLabelConfig;

  static override readonly xtype: string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.statusLabel";

  #text: string = null;

  get text(): string {
    return this.#text;
  }

  set text(value: string) {
    this.#text = value;
  }

  #error: boolean = false;

  get error(): boolean {
    return this.#error;
  }

  set error(value: boolean) {
    this.#error = value;
  }

  constructor(config: Config<StatusLabel> = null) {
    super(ConfigUtils.apply(Config(StatusLabel, {
      height: 40,
      items: [
        Config(IconDisplayField, {
          ui: DisplayFieldSkin.GREEN.getSkin(),
          scale: "medium",
          iconCls: CoreIcons_properties.check_circle,
          plugins: [
            Config(BindPropertyPlugin, {
              componentProperty: "hidden",
              bindTo: ValueExpressionFactory.createFromValue(config.error),
            }),
          ],
        }),
        Config(IconDisplayField, {
          ui: DisplayFieldSkin.RED.getSkin(),
          scale: "medium",
          iconCls: CoreIcons_properties.error_circle,
          plugins: [
            Config(BindPropertyPlugin, {
              componentProperty: "hidden",
              bindTo: ValueExpressionFactory.createFromValue(!config.error),
            }),
          ],
        }),
        Config(Container, { width: 6 }),
        Config(DisplayField, { value: config.text }),
      ],
      layout: Config(HBoxLayout, { align: "stretch" }),
    }), config));
  }
}

export default StatusLabel;
