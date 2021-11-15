import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import BriefingComboBox from "./BriefingComboBox";
import BriefingSelectorItemPanelBase from "./BriefingSelectorItemPanelBase";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import IconButton from "@coremedia/studio-client.ext.ui-components/components/IconButton";
import IconDisplayField from "@coremedia/studio-client.ext.ui-components/components/IconDisplayField";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import BindVisibilityPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindVisibilityPlugin";
import VerticalSpacingPlugin from "@coremedia/studio-client.ext.ui-components/plugins/VerticalSpacingPlugin";
import ButtonSkin from "@coremedia/studio-client.ext.ui-components/skins/ButtonSkin";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import Component from "@jangaroo/ext-ts/Component";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import AnchorLayout from "@jangaroo/ext-ts/layout/container/Anchor";
import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import VBoxLayout from "@jangaroo/ext-ts/layout/container/VBox";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface BriefingSelectorItemPanelConfig extends Config<BriefingSelectorItemPanelBase> {
}



    class BriefingSelectorItemPanel extends BriefingSelectorItemPanelBase{
  declare Config: BriefingSelectorItemPanelConfig;

  static override readonly xtype:string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.briefingSelectorItemPanel";

  constructor(config:Config<BriefingSelectorItemPanel> = null){
    super((()=> ConfigUtils.apply(Config(BriefingSelectorItemPanel, {

  items:[
    Config(Container, {
      items:[
        Config(BriefingComboBox, { briefingInfoExpression: this.getBriefingInfoExpression(),
                                    itemId:  BriefingSelectorItemPanelBase.BRIEFING_COMBOBOX_ITEM_ID,
                                    flex: 1,
                                    briefingsExpression: this.getBriefingsExpression()}),
        Config(Container, { width: 6}),
        Config(IconButton, { iconCls:  CoreIcons_properties.reload,
                       itemId: "briefingReloadButton",
                       tooltip: FeedbackHubSearchmetrics_properties.searchmetrics_briefing_reload_button,
                       ariaLabel:  FeedbackHubSearchmetrics_properties.searchmetrics_briefing_reload_button,
                       handler: bind(this,this.reloadBriefings)})
      ],
      layout: Config(HBoxLayout, { align: "stretch"
      })
    }),
    Config(Container, {
      items:[
        Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(),
                      value: FeedbackHubSearchmetrics_properties.searchmetrics_briefing_content_title}),
        Config(Container, { flex: 1, style: "border: 1px solid #c7c7c7; border-radius: 3px; padding: 6px;",
          items:[
            Config(DisplayField, { html: true, itemId: "briefingContent",
              plugins:[
                Config(BindPropertyPlugin, { componentProperty: "value",
                                       ifUndefined: FeedbackHubSearchmetrics_properties.searchmetrics_briefing_loading,
                                       bindTo: this.getBriefingContentExpression()})
              ]
            })
          ]
        })
      ],
      layout: Config(VBoxLayout, { align: "stretch"
      }),
      plugins:[
        Config(BindVisibilityPlugin, { bindTo: this.getBriefingInfoExpression()})
      ]
    }),
    Config(Container, {
      items:[
        Config(DisplayField, { ui:  DisplayFieldSkin.BOLD.getSkin(),
                      value: FeedbackHubSearchmetrics_properties.searchmetrics_briefing_content}),
        Config(Container, {
          items:[
            Config(IconDisplayField, { iconCls:  CoreIcons_properties.warning,
                                 margin: "0 6 0 0",
                                 ui:  DisplayFieldSkin.ORANGE.getSkin()}),
            Config(DisplayField, {
                    flex: 1,
                    value: FeedbackHubSearchmetrics_properties.searchmetrics_briefing_content_help})
          ],
          layout: Config(HBoxLayout, { align: "stretch"
          })
        })
      ],
      plugins:[
        Config(BindVisibilityPlugin, { bindTo: this.getBriefingInfoExpression()})
      ],
      layout: Config(VBoxLayout, { align: "stretch"
      })
    }),
    Config(Component, { height: 6}),
    Config(Container, {
      items:[
        Config(Button, { ui:  ButtonSkin.MATERIAL_PRIMARY.getSkin(),
                handler: bind(this,this.applyBriefingSelection),
                text:  FeedbackHubSearchmetrics_properties.searchmetrics_briefing_apply_button})
      ],
      layout: Config(HBoxLayout, { align: "stretch", pack: "end"
      }),
      plugins:[
        Config(BindVisibilityPlugin, { bindTo: this.getBriefingInfoExpression()})
      ]
    })
  ],
  defaultType: Component.xtype,
  defaults:Config<Component>({ anchor: "100%"
  }),
  layout: Config(AnchorLayout),
  plugins:[
    Config(VerticalSpacingPlugin)
  ]
}),config))());
  }}
export default BriefingSelectorItemPanel;
