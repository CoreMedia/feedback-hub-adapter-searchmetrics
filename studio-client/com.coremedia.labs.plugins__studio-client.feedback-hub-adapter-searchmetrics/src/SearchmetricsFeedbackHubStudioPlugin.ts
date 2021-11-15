import Config from "@jangaroo/runtime/Config";
import FeedbackHubSearchmetrics_properties from "./FeedbackHubSearchmetrics_properties";
import BriefingSelectorItemPanel from "./itemtypes/BriefingSelectorItemPanel";
import DuplicatesListItemPanel from "./itemtypes/DuplicatesListItemPanel";
import KeywordListItemPanel from "./itemtypes/KeywordListItemPanel";
import QuestionsListItemPanel from "./itemtypes/QuestionsListItemPanel";
import CopyResourceBundleProperties from "@coremedia/studio-client.main.editor-components/configuration/CopyResourceBundleProperties";
import StudioPlugin from "@coremedia/studio-client.main.editor-components/configuration/StudioPlugin";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import feedbackService from "@coremedia/studio-client.main.feedback-hub-editor-components/feedbackService";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
interface SearchmetricsFeedbackHubStudioPluginConfig extends Config<StudioPlugin> {
}



    class SearchmetricsFeedbackHubStudioPlugin extends StudioPlugin{
  declare Config: SearchmetricsFeedbackHubStudioPluginConfig;

  static readonly xtype:string = "com.coremedia.labs.plugins.feedbackhub.searchmetrics.config.searchmetricsFeedbackHubStudioPlugin";

  constructor(config:Config<SearchmetricsFeedbackHubStudioPlugin> = null){
    super((()=>{this.#__initialize__(config);
    return  ConfigUtils.apply(Config(SearchmetricsFeedbackHubStudioPlugin, {

  configuration:[
    new CopyResourceBundleProperties({
            destination: resourceManager.getResourceBundle(null,FeedbackHub_properties),
            source: resourceManager.getResourceBundle(null,FeedbackHubSearchmetrics_properties)})
  ]


}),config);})());
  }

  #__initialize__(config:Config<SearchmetricsFeedbackHubStudioPlugin>):void {
      feedbackService._.registerFeedbackItemPanel("searchmetricsBriefingSelector", Config(BriefingSelectorItemPanel));
      feedbackService._.registerFeedbackItemPanel("searchmetricsKeywordList", Config(KeywordListItemPanel));
      feedbackService._.registerFeedbackItemPanel("searchmetricsQuestionsList", Config(QuestionsListItemPanel));
      feedbackService._.registerFeedbackItemPanel("searchmetricsDuplicatesList", Config(DuplicatesListItemPanel));
    }}
export default SearchmetricsFeedbackHubStudioPlugin;
