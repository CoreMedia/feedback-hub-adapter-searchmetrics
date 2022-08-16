/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  extNamespace: "com.coremedia.labs.plugins.feedbackhub.searchmetrics",
  sencha: {
    studioPlugins: [
      {
        mainClass: "__coremedia_labs.studio_client_main_feedback_hub_adapter_searchmetrics.SearchmetricsFeedbackHubStudioPlugin",
        name: "FeedbackHub for Searchmetrics",
      },
    ],
  },
};
