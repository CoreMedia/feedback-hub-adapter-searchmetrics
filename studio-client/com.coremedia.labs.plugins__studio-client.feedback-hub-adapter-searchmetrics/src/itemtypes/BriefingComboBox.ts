import Config from "@jangaroo/runtime/Config";
import { asConfig, bind } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";
import BriefingComboBoxBase from "./BriefingComboBoxBase";
import BindListPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindListPlugin";
import BindPropertyPlugin from "@coremedia/studio-client.ext.ui-components/plugins/BindPropertyPlugin";
import DataField from "@coremedia/studio-client.ext.ui-components/store/DataField";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface BriefingComboBoxConfig extends Config<BriefingComboBoxBase> {
}



    class BriefingComboBox extends BriefingComboBoxBase{
  declare Config: BriefingComboBoxConfig;

  static override readonly xtype:string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.briefingComboBox";

  constructor(config:Config<BriefingComboBox> = null){
    super((()=> ConfigUtils.apply(Config(BriefingComboBox, {
        fieldLabel:  FeedbackHubSearchmetrics_properties.searchmetrics_briefing,
        emptyText:  FeedbackHubSearchmetrics_properties.searchmetrics_briefing_emptyText,
        labelAlign: "left",
        labelSeparator: ":",
        anyMatch: true,
        editable: false,
        valueField:  SearchmetricsPropertyNames.ID,
        displayField:  SearchmetricsPropertyNames.STORY,
        valueNotFoundText:  FeedbackHubSearchmetrics_properties.searchmetrics_briefing_emptyText,

  ...ConfigUtils.append({plugins: [
    Config(BindListPlugin, { bindTo: config.briefingsExpression,
                       sortField:  SearchmetricsPropertyNames.STORY,
      fields:[
        Config(DataField, { name:  SearchmetricsPropertyNames.ID}),
        Config(DataField, { name:  SearchmetricsPropertyNames.STORY,
                      sortType: (s:string):string => s?s.toLowerCase():s})
      ]
    }),
    Config(BindPropertyPlugin, { bindTo: config.briefingInfoExpression,
                           transformer: bind(this,this.briefingTransformer),
                           reverseTransformer: bind(this,this.briefingReverseTransformer),
                           bidirectional: true,
                           skipIfUndefined: true,
                           componentEvent: "change"})
  ]})

}),config))());
  }}
export default BriefingComboBox;
