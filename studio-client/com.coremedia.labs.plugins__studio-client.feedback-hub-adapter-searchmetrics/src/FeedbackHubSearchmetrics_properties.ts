import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";

/**
 * Interface values for ResourceBundle "FeedbackHubSearchmetrics".
 * @see FeedbackHubSearchmetrics_properties#INSTANCE
 */
interface FeedbackHubSearchmetrics_properties {

  searchmetrics_iconCls: string;
  searchmetrics_title: string;
  searchmetrics_ariaLabel: string;
  searchmetrics_tooltip: string;
/**
 * Header Panel
 */
  searchmetrics_button_tooltip: string;
  searchmetrics_button_url: string;
  searchmetrics_button_briefing_url: string;
  searchmetrics_briefing: string;
  searchmetrics_briefing_reload_button: string;
  searchmetrics_briefing_title: string;
  searchmetrics_briefing_content: string;
  searchmetrics_briefing_content_title: string;
  searchmetrics_briefing_no_content: string;
  searchmetrics_briefing_apply_button: string;
  searchmetrics_briefing_content_help: string;
  searchmetrics_briefing_emptyText: string;
  searchmetrics_briefing_not_selected: string;
  searchmetrics_briefing_title_not_selected: string;
  searchmetrics_briefing_apply_title: string;
  searchmetrics_briefing_apply_text: string;
  searchmetrics_briefing_loading: string;
  searchmetrics_general_tab_title: string;
  searchmetrics_content_tab_title: string;
  searchmetrics_keywords_tab_title: string;
  searchmetrics_questions_tab_title: string;
  searchmetrics_competitors_tab_title: string;
/**
 * General Panel
 */
  searchmetrics_content_score: string;
  searchmetrics_score_details: string;
  searchmetrics_readability: string;
  searchmetrics_selected_briefing: string;
  searchmetrics_word_count: string;
  searchmetrics_sentence_structure: string;
  searchmetrics_keyword_coverage: string;
  searchmetrics_repetitions: string;
  searchmetrics_main_scores: string;
  searchmetrics_score_easy: string;
  searchmetrics_score_difficult: string;
  searchmetrics_must_have_keywords: string;
  searchmetrics_recommended_keywords: string;
  searchmetrics_additional_keywords: string;
  searchmetrics_other_questions: string;
  searchmetrics_other_questions_de: string;
  searchmetrics_competitors_duplicate_check: string;
  searchmetrics_duplicated_page: string;
  searchmetrics_similarity: string;
  searchmetrics_question_list: string;
  searchmetrics_show_analyzed_pages: string;
  searchmetrics_load_more: string;
  searchmetrics_questions: string;
  searchmetrics_loading: string;
  searchmetrics_duplicate_warning: string;
  searchmetrics_duplicate_ok: string;
/**
 * Tips
 */
  searchmetrics_qtip_must_have_keywords: string;
  searchmetrics_qtip_recommended_keywords: string;
  searchmetrics_qtip_additional_keywords: string;
  searchmetrics_qtip_content_score: string;
  searchmetrics_qtip_word_count: string;
  searchmetrics_qtip_sentence_structure: string;
  searchmetrics_qtip_repetitions: string;
  searchmetrics_qtip_keyword_coverage: string;
  searchmetrics_qtip_readability_score: string;
  searchmetrics_qtip_duplicates: string;
/**
 * Searchmetrics Error Messages
 */
  searchmetrics_error_API_KEY_NOT_SET: string;
  searchmetrics_error_API_SECRET_NOT_SET: string;
  searchmetrics_error_PROJECT_ID_NOT_SET: string;
  searchmetrics_error_PROPERTY_NOT_SET: string;
}

/**
 * Singleton for the current user Locale's instance of ResourceBundle "FeedbackHubSearchmetrics".
 * @see FeedbackHubSearchmetrics_properties
 */
const FeedbackHubSearchmetrics_properties: FeedbackHubSearchmetrics_properties = {
  searchmetrics_iconCls: CoreIcons_properties.searchmetrics,
  searchmetrics_title: "Searchmetrics",
  searchmetrics_ariaLabel: "Searchmetrics",
  searchmetrics_tooltip: "Check article text with a Searchmetrics briefing",
  searchmetrics_button_tooltip: "Open in Searchmetrics",
  searchmetrics_button_url: "https://app.searchmetrics.com/suite/de/content-experience/content-creation/brief-creator/topicgraph/{0}",
  searchmetrics_button_briefing_url: "https://app.searchmetrics.com/suite/en/content-experience/content-creation/content-editor/{0}",
  searchmetrics_briefing: "Briefing",
  searchmetrics_briefing_reload_button: "Reload Briefings",
  searchmetrics_briefing_title: "Briefing Title",
  searchmetrics_briefing_content: "Searchmetrics Briefing Content",
  searchmetrics_briefing_content_title: "Text stored in Searchmetrics",
  searchmetrics_briefing_no_content: "The Briefing text is empty.",
  searchmetrics_briefing_apply_button: "Apply Briefing",
  searchmetrics_briefing_content_help: "This is the text that is currently stored in Searchmetrics for this briefing. Once you apply the selection, this content will be overwritten with text of your article.",
  searchmetrics_briefing_emptyText: "Select a briefing",
  searchmetrics_briefing_not_selected: "<i>Your chosen brief will be shown here.<\/i>",
  searchmetrics_briefing_title_not_selected: "<i>The briefing title will be shown here.<\/i>",
  searchmetrics_briefing_apply_title: "Apply Briefing",
  searchmetrics_briefing_apply_text: "When you apply this briefing, the text in Searchmetrics will be overwritten and cannot be recovered.",
  searchmetrics_briefing_loading: "<i style=\"color:#c7c7c7;\">Loading briefing text...<\/i>",
  searchmetrics_general_tab_title: "General",
  searchmetrics_content_tab_title: "Content",
  searchmetrics_keywords_tab_title: "Keywords",
  searchmetrics_questions_tab_title: "Questions",
  searchmetrics_competitors_tab_title: "Competitors",
  searchmetrics_content_score: "Content Score",
  searchmetrics_score_details: "Score Details",
  searchmetrics_readability: "Readability",
  searchmetrics_selected_briefing: "Selected Briefing: {0}",
  searchmetrics_word_count: "Word Count",
  searchmetrics_sentence_structure: "Sentence Structure",
  searchmetrics_keyword_coverage: "Keyword Coverage",
  searchmetrics_repetitions: "Repetitions",
  searchmetrics_main_scores: "Main Scores",
  searchmetrics_score_easy: "Easy",
  searchmetrics_score_difficult: "Difficult",
  searchmetrics_must_have_keywords: "Must-Have Keywords",
  searchmetrics_recommended_keywords: "Recommended Keywords",
  searchmetrics_additional_keywords: "Additional Keywords",
  searchmetrics_other_questions: "Other",
  searchmetrics_other_questions_de: "Andere",
  searchmetrics_competitors_duplicate_check: "Competitors Duplicate Check",
  searchmetrics_duplicated_page: "Duplicated Page",
  searchmetrics_similarity: "Similarity",
  searchmetrics_question_list: "Questions",
  searchmetrics_show_analyzed_pages: "Click here to show analyzed pages in Searchmetrics",
  searchmetrics_load_more: "Load more ({0})",
  searchmetrics_questions: "{0} questions",
  searchmetrics_loading: "Loading Content Scores...",
  searchmetrics_duplicate_warning: "Your text severely duplicates {0} webpage/s.",
  searchmetrics_duplicate_ok: "No duplicates found. Very good!",
  searchmetrics_qtip_must_have_keywords: "<b>Must-Have Keywords<\/b><br/><br/> Must-Have Keywords are central to the topic(s) you are targeting. These terms are being heavily used in the content of your competitors. Incorporating these terms into your content is critical in order for your content to perform well in the search engines.",
  searchmetrics_qtip_recommended_keywords: "<b>Recommended Keywords<\/b><br/><br/> Recommended Keywords are terms that many (but not all) of your competitors are using. These terms are associated with your topic(s) but are not as central as the Must-Have Keywords. Be sure to incorporate Recommended Keywords in order to create the holistic content your target audience is searching for.",
  searchmetrics_qtip_additional_keywords: "<b>Additional Keywords<\/b><br/><br/> Additional Keywords are terms that are not central to the topic(s) you are targeting and may not even be strictly relevant. But including these terms in your content can add to the uniqueness of your content. Search engines reward uniqueness, so incorporating these terms can help your content outrank competitors and stand out in the eyes of your target audience.",
  searchmetrics_qtip_content_score: "<b>Content Score<\/b><br><br>The Content Score represents how holistically and comprehensively your content addresses the topic(s). It is based on 4 components: word count, sentence structure, keyword coverage, and repetitions. We believe good text are comprised of all 4 factors equally.<br><br>The higher the Content Score, the better the coverage of the given topic(s) and the higher the potential for your content to perform successfully in the search engine.",
  searchmetrics_qtip_word_count: "<b>How is word count calculated?<\/b><br/><br/> This score depicts the gap between the amount of words in the text and the target word count. The target word count has been set by the creator of the brief and is usually based on the word count of the competing pages. The target value influences the recommended keyword frequencies and affects the overall Content Score.",
  searchmetrics_qtip_sentence_structure: "<b>What is sentence structure?<\/b><br/><br/> This new score assesses how well your text is structured. With our new deep learning algorithm, we can detect when you are not using natural language, and calculate the score accordingly. For ex. If you write a phrase such as \"I eats fish\", we are able to detect that this is not proper language and this will affect your score.",
  searchmetrics_qtip_repetitions: "<b>How often are you repeating your text?<\/b><br/><br/> If you are heavily repeating sentences and phrases within your text, your score will be affected. This does not apply to individual words, only phrases & sentences.",
  searchmetrics_qtip_keyword_coverage: "<b>Are you using the right keywords?<\/b><br/><br/> This score calculates your keyword coverage, based on our recommendations. The more keywords that you add in your text, the higher the score.",
  searchmetrics_qtip_readability_score: "<b>Readability Score<\/b><br/><br/>The readability score, inspired by the Flesch-Kincaid readability tests, assesses how difficult a text is to read. This is measured by the length of sentences and the number of syllables in a word.<br><br>The scoring system ranges from 0-10:<ul><li><b>0-3:<\/b> more difficult to read<\/li><li><b>3-5:<\/b> less difficult to read<\/li><li><b>5-7:<\/b> generally understood<\/li><li><b>7-10:<\/b> easy to read & understand<\/li><\/ul>",
  searchmetrics_qtip_duplicates: "<b>Competitors Duplicate Check<\/b><br/><br/>The duplicate check unveils the plagiarisms that occure in your text - either because you've copied text from a different source or because your text has been copied by someone else.<br><br>The duplicate check analyzes your text and compares it against the text of the webpages that write about the same topics (top competitors pages). It then lists every webpages' similarity and seperates 3 categories from another:<ul><li>Severe duplicates (51-100%): They occur when a large amount of text has obviously been copied from one of the top ranking webpages.<\/li><li>Moderate duplicates (16-50%): They occur when a significant amount of your text resembles some segments of the top ranking webpages.<\/li><li>No duplicates (<16%): No duplicates have been detected.<\/li><\/ul>Duplicate content, in general, increases the chance of unfavorable webpages performance results. Hence, the smaller the similarity, the better.",
  searchmetrics_error_API_KEY_NOT_SET: "Please provide a valid apiKey in the configuration for {0}.",
  searchmetrics_error_API_SECRET_NOT_SET: "Please provide a valid sharedKey in the configuration for {0}.",
  searchmetrics_error_PROJECT_ID_NOT_SET: "Please provide a valid projectId in the configuration for {0}.",
  searchmetrics_error_PROPERTY_NOT_SET: "Please provide a valid propertyName in the configuration for {0}."
};

export default FeedbackHubSearchmetrics_properties;
