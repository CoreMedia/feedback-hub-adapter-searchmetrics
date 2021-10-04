package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics;

import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.FeedbackItemDefaultCollections;
import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.adapter.text.TextFeedbackHubAdapter;
import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItemFactory;
import com.coremedia.feedbackhub.items.GaugeFeedbackItem;
import com.coremedia.feedbackhub.items.LabelFeedbackItem;
import com.coremedia.feedbackhub.items.PercentageBarFeedbackItem;
import com.coremedia.feedbackhub.items.RatingBarFeedbackItem;
import com.coremedia.feedbackhub.items.ScoreBarFeedbackItem;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items.BriefingSelectorFeedbackItem;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items.DuplicatesListFeedbackItem;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items.KeywordListFeedbackItem;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.items.QuestionsListFeedbackItem;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsException;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.labs.plugins.searchmetrics.documents.Keyword;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 *
 */
@DefaultAnnotation(NonNull.class)
public class SearchmetricsFeedbackAdapter implements TextFeedbackHubAdapter {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsFeedbackAdapter.class);
  private final SearchmetricsSettings settings;
  private final SearchmetricsService searchmetricsService;

  SearchmetricsFeedbackAdapter(SearchmetricsSettings settings,
                               SearchmetricsService searchmetricsService) {
    this.settings = settings;
    this.searchmetricsService = searchmetricsService;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> analyzeText(FeedbackContext context, Map<String, String> textProperties, @Nullable Locale locale) {
    try {
      Content content = (Content) context.getEntity();
      String contentId = content.getId();
      List<Briefing> briefings = searchmetricsService.getBriefings(settings, false);

      String text = textProperties.values().stream().filter(p -> !StringUtils.isEmpty(p)).collect(Collectors.joining(" "));
      String briefingId = null;
      String briefingContent = null;

      Briefing briefing = searchmetricsService.getContentBriefing(settings, contentId);
      if (briefing != null) {
        briefingId = briefing.getBriefId();
        briefingContent = briefing.getLatestContentVersion().getContent();
        briefing = searchmetricsService.updateBriefing(settings, briefing.getBriefId(), text);
      }
      else {
        LOG.info("No briefing assigment for content {}, skipping Searchmetrics validation.", contentId);
      }


      List<FeedbackItem> items = new ArrayList<>();
      items.add(FeedbackItemFactory.createFeedbackLink("https://app.searchmetrics.com/"));
      items.add(new BriefingSelectorFeedbackItem(SearchmetricsFeedbackCollections.GENERAL, briefings, briefingId, briefingContent));

      if (briefing != null) {
        items.add(LabelFeedbackItem.builder()
                .withCollection(FeedbackItemDefaultCollections.header.name())
                .withLabel("searchmetrics_selected_briefing", briefing.getBriefName())
                .build());

        //score gauge
        int goal = briefing.getTargetContentScore();

        GaugeFeedbackItem gaugeItem = GaugeFeedbackItem.builder()
                .withCollection(SearchmetricsFeedbackCollections.CONTENT)
                .withValue(briefing.getLatestContentVersion().getContentScore() * 100, goal)
                .withTitle("searchmetrics_content_score")
                .withHelp("searchmetrics_qtip_content_score")
                .build();
        items.add(gaugeItem);

        //scoring details
        ScoreBarFeedbackItem wordCountItem = ScoreBarFeedbackItem.builder()
                .withCollection(SearchmetricsFeedbackCollections.CONTENT)
                .withTitle("searchmetrics_score_details")
                .withHelp("searchmetrics_qtip_word_count")
                .withLabel("searchmetrics_word_count")
                .withValue(briefing.getLatestContentVersion().getWordCount(), briefing.getTargetWordCount())
                .build();
        items.add(wordCountItem);


        ScoreBarFeedbackItem sentenceStructureItem = ScoreBarFeedbackItem.builder()
                .withCollection(SearchmetricsFeedbackCollections.CONTENT)
                .withTitle(null)
                .withHelp("searchmetrics_qtip_sentence_structure")
                .withLabel("searchmetrics_sentence_structure")
                .withValue(Math.round(briefing.getLatestContentVersion().getNaturalLanguageScore() * 100))
                .build();
        items.add(sentenceStructureItem);


        ScoreBarFeedbackItem keywordCoverageItem = ScoreBarFeedbackItem.builder()
                .withCollection(SearchmetricsFeedbackCollections.CONTENT)
                .withTitle(null)
                .withHelp("searchmetrics_qtip_keyword_coverage")
                .withLabel("searchmetrics_keyword_coverage")
                .withValue(Math.round(briefing.getLatestContentVersion().getCoverageScore() * 100))
                .build();
        items.add(keywordCoverageItem);


        PercentageBarFeedbackItem repetitionsItem = PercentageBarFeedbackItem.builder()
                .withCollection(SearchmetricsFeedbackCollections.CONTENT)
                .withTitle(null)
                .withHelp("searchmetrics_qtip_repetitions")
                .withLabel("searchmetrics_repetitions")
                .withValue(Math.round(briefing.getLatestContentVersion().getRepetitionScore() * 100))
                .build();
        items.add(repetitionsItem);


        int readability = Math.round(briefing.getLatestContentVersion().getReadabilityScore() * 100);
        if (readability == 0) {
          readability = 1;
        }

        RatingBarFeedbackItem readabilityItem = RatingBarFeedbackItem.builder()
                .withCollection(SearchmetricsFeedbackCollections.CONTENT)
                .withTitle("searchmetrics_readability")
                .withHelp("searchmetrics_qtip_readability_score")
                .withMinLabel("searchmetrics_score_difficult")
                .withMaxLabel("searchmetrics_score_easy")
                .withValue(readability, 10)
                .build();
        items.add(readabilityItem);


        //keyword tab
        addMainScores(items, SearchmetricsFeedbackCollections.KEYWORDS, briefing);

        List<Keyword> briefKeywords = briefing.getBriefKeywords();
        List<Keyword> mustHave = new ArrayList<>();
        List<Keyword> recommended = new ArrayList<>();
        List<Keyword> additional = new ArrayList<>();

        for (Keyword briefKeyword : briefKeywords) {
          String category = briefKeyword.getCategory();
          if(category.equalsIgnoreCase(KeywordListFeedbackItem.MUST_HAVE)) {
            mustHave.add(briefKeyword);
          }
          else if(category.equalsIgnoreCase(KeywordListFeedbackItem.RECOMMENDED)) {
            mustHave.add(briefKeyword);
          }
          else if(category.equalsIgnoreCase(KeywordListFeedbackItem.ADDITIONAL)) {
            mustHave.add(briefKeyword);
          }
        }

        items.add(new KeywordListFeedbackItem(SearchmetricsFeedbackCollections.KEYWORDS, "searchmetrics_must_have_keywords", mustHave, KeywordListFeedbackItem.MUST_HAVE, "searchmetrics_qtip_must_have_keywords"));
        items.add(new KeywordListFeedbackItem(SearchmetricsFeedbackCollections.KEYWORDS, "searchmetrics_additional_keywords", recommended, KeywordListFeedbackItem.RECOMMENDED, "searchmetrics_qtip_recommended_keywords"));
        items.add(new KeywordListFeedbackItem(SearchmetricsFeedbackCollections.KEYWORDS, "searchmetrics_recommended_keywords", additional, KeywordListFeedbackItem.ADDITIONAL, "searchmetrics_qtip_additional_keywords"));

        //competitors tab
        addMainScores(items, SearchmetricsFeedbackCollections.QUESTIONS, briefing);
        items.add(new QuestionsListFeedbackItem(SearchmetricsFeedbackCollections.QUESTIONS, "searchmetrics_question_list", briefing));

        addMainScores(items, SearchmetricsFeedbackCollections.COMPETITORS, briefing);
        items.add(new DuplicatesListFeedbackItem(SearchmetricsFeedbackCollections.COMPETITORS, "searchmetrics_competitors_duplicate_check", "searchmetrics_qtip_duplicates", briefingId, briefing.getDuplicationInformation()));
      }
      else {
        //add empty default tabs
        items.add(LabelFeedbackItem.builder()
                .withCollection(FeedbackItemDefaultCollections.header.name())
                .withLabel("searchmetrics_briefing_emptyText")
                .build());
        items.add(FeedbackItemFactory.createEmptyItem(SearchmetricsFeedbackCollections.GENERAL));
        items.add(FeedbackItemFactory.createEmptyItem(SearchmetricsFeedbackCollections.CONTENT));
        items.add(FeedbackItemFactory.createEmptyItem(SearchmetricsFeedbackCollections.KEYWORDS));
        items.add(FeedbackItemFactory.createEmptyItem(SearchmetricsFeedbackCollections.QUESTIONS));
        items.add(FeedbackItemFactory.createEmptyItem(SearchmetricsFeedbackCollections.COMPETITORS));
      }

      return CompletableFuture.completedFuture(items);
    } catch (SearchmetricsException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private void addMainScores(List<FeedbackItem> items, String collection, Briefing briefing) {
    int goal = briefing.getTargetContentScore();

    ScoreBarFeedbackItem mainScoreItem = ScoreBarFeedbackItem.builder()
            .withCollection(collection)
            .withTitle("searchmetrics_main_scores")
            .withHelp("searchmetrics_qtip_content_score")
            .withLabel("searchmetrics_content_score")
            .withValue(briefing.getLatestContentVersion().getContentScore() * 100, 100, goal)
            .build();
    items.add(mainScoreItem);


    ScoreBarFeedbackItem wordCountItem = ScoreBarFeedbackItem.builder()
            .withCollection(collection)
            .withTitle(null)
            .withHelp("searchmetrics_qtip_word_count")
            .withLabel("searchmetrics_word_count")
            .withValue(briefing.getLatestContentVersion().getContent().length(), briefing.getContentSequenceId())
            .build();
    items.add(wordCountItem);
  }
}
