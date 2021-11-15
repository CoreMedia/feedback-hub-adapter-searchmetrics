/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  extName: "com.coremedia.labs.plugins__studio-client.feedback-hub-adapter-searchmetrics",
  extNamespace: "com.coremedia.labs.plugins.feedbackhub.searchmetrics",
  sencha: {
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.feedbackhub.searchmetrics.SearchmetricsFeedbackHubStudioPlugin",
        name: "FeedbackHub for Searchmetrics",
      },
    ],
  },
  command: {
    build: {
      ignoreTypeErrors: true
    },
  },
};
