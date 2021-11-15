import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import KeywordScoreBarBase from "./KeywordScoreBarBase";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import IconDisplayField from "@coremedia/studio-client.ext.ui-components/components/IconDisplayField";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface KeywordScoreBarConfig extends Config<KeywordScoreBarBase> {
}



    class KeywordScoreBar extends KeywordScoreBarBase{
  declare Config: KeywordScoreBarConfig;

  static override readonly xtype:string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.keywordScoreBar";

  constructor(config:Config<KeywordScoreBar> = null){
    super((()=> ConfigUtils.apply(Config(KeywordScoreBar, {

  items:[
    Config(IconDisplayField, { iconCls:  CoreIcons_properties.approve,
                         ui:  this.getIconSkin(config),
      plugins:[
        Config(BindPropertyPlugin, { componentProperty: "disabled", bindTo: this.getDisabledExpression(config)})
      ]
    }),
    Config(IconDisplayField, { flex: 1,
      plugins:[
        Config(BindPropertyPlugin, { componentProperty: "value", bindTo: config.bindTo.extendBy(config.labelPropertyName)})
      ]
    }),
    Config(DisplayField, { itemId:  this.BAR_ITEM_ID, html: true, width: 150}),
    Config(Container, { width: 50,
      items:[
        Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(), margin: "-3 2 0 0",
          plugins:[
            Config(BindPropertyPlugin, { componentProperty: "value", bindTo: config.bindTo.extendBy(config.valuePropertyName),
                                   transformer: bind(this,this.formatScore)})
          ]
        }),
        Config(DisplayField, { ui:  DisplayFieldSkin.SUB_LABEL_READONLY.getSkin(), value: "/"}),
        Config(DisplayField, { ui:  DisplayFieldSkin.SUB_LABEL_READONLY.getSkin(),
          plugins:[
            Config(BindPropertyPlugin, { componentProperty: "value",
                                   bindTo: config.bindTo.extendBy(config.targetValuePropertyName)})
          ]
        })
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "end"
      })
    })
  ],
  layout: Config(HBoxLayout, { align: "stretch"
  })
}),config))());
  }}
export default KeywordScoreBar;
