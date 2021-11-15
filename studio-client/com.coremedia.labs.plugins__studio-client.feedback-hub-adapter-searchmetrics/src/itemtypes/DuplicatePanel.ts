import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import DuplicatePanelBase from "./DuplicatePanelBase";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackHubPropertyNames from "@coremedia/studio-client.main.feedback-hub-editor-components/util/FeedbackHubPropertyNames";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
interface DuplicatePanelConfig extends Config<DuplicatePanelBase> {
}



    class DuplicatePanel extends DuplicatePanelBase{
  declare Config: DuplicatePanelConfig;

  static override readonly xtype:string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.duplicatePanel";

  constructor(config:Config<DuplicatePanel> = null){
    super((()=> ConfigUtils.apply(Config(DuplicatePanel, {

  items:[
    Config(Container, { flex: 1,
      items:[
        Config(Button, { ui:  ButtonSkin.SIMPLE_PRIMARY.getSkin(), handler: bind(this,this.linkClick),
          plugins:[
            Config(BindPropertyPlugin, { componentProperty: "text",
                                   bindTo: config.bindTo.extendBy(FeedbackHubPropertyNames.PROPERTY_URL)})
          ]
        })
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "start"
      })
    }),
    Config(Container, { width: 80,
      items:[
        Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(),
          plugins:[
            Config(BindPropertyPlugin, { componentProperty: "value", bindTo: config.bindTo.extendBy("duplication_score"),
                                   transformer: bind(this,this.formatPercentage)})
          ]
        })
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "center"
      })
    })
  ],
  layout: Config(HBoxLayout, { align: "stretch", pack: "start"
  })
}),config))());
  }}
export default DuplicatePanel;
