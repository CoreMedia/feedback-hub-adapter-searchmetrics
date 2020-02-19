package com.coremedia.blueprint.feedbackhub.searchmetrics;

import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.blueprint.searchmetrics.documents.BriefingInfo;
import com.coremedia.blueprint.searchmetrics.documents.ContentValidation;
import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.provider.ContentFeedbackProvider;
import com.coremedia.feedbackhub.provider.FeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 */
@DefaultAnnotation(NonNull.class)
public class SearchmetricsContentFeedbackProvider implements ContentFeedbackProvider {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsContentFeedbackProvider.class);
  private SearchmetricsSettings settings;
  private SearchmetricsService searchmetricsService;

  SearchmetricsContentFeedbackProvider(SearchmetricsSettings settings,
                                       SearchmetricsService searchmetricsService) {
    this.settings = settings;
    this.searchmetricsService = searchmetricsService;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> provideFeedback(Content content) {
    List<BriefingInfo> briefings = searchmetricsService.getBriefings(settings);
    ContentValidation contentValidation = null;

    Briefing briefing = searchmetricsService.getContentBriefing(settings, content.getId());
    if (briefing != null) {
      contentValidation = searchmetricsService.validateContent(settings, briefing.getId(), content);
      if(contentValidation.getDuplicationCheckResults() != null) {
        contentValidation.getDuplicationCheckResults().sort((o2, o1) -> o1.getDuplicationScore() - o2.getDuplicationScore());
      }
    }
    else {
      LOG.info("No briefing assigment for content {}, skipping searchmetrics validation.", content.getId());
    }

    SearchmetricsFeedbackItem item = new SearchmetricsFeedbackItem(content, settings.getPropertyName(), briefings, briefing, contentValidation);
    return CompletableFuture.completedFuture(asFeedbackItems(item));
  }

  @Override
  public String getFeedbackType() {
    return SearchmetricsFeedbackItem.TYPE;
  }

  private Collection<FeedbackItem> asFeedbackItems(SearchmetricsFeedbackItem feedbackItem) {
    return Collections.singleton(feedbackItem);
  }

}
