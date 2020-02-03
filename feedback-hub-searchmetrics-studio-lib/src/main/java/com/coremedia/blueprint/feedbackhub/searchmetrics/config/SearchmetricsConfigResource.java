package com.coremedia.blueprint.feedbackhub.searchmetrics.config;

import com.coremedia.blueprint.feedbackhub.searchmetrics.SearchmetricsContentFeedbackProviderFactory;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.cap.common.IdHelper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(value = "searchmetrics", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchmetricsConfigResource {

  private SearchmetricsService service;
  private SearchmetricsContentFeedbackProviderFactory feedbackProviderFactory;

  public SearchmetricsConfigResource(SearchmetricsService searchmetricsService, SearchmetricsContentFeedbackProviderFactory feedbackProviderFactory) {
    service = searchmetricsService;
    this.feedbackProviderFactory = feedbackProviderFactory;
  }

  @PostMapping("briefingmapping")
  public void setBriefingMapping(@RequestParam("contentId") String contentId,
                                 @RequestParam("briefingId") String briefingId) {
    String id = contentId.substring(contentId.lastIndexOf('/') + 1);
    String capId = IdHelper.formatContentId(id);

    SearchmetricsSettings settings = feedbackProviderFactory.getSettings();
    service.setContentBriefing(settings, capId, briefingId);
  }

  @GetMapping("briefings/refresh")
  public Boolean refreshBriefings() {
    SearchmetricsSettings settings = feedbackProviderFactory.getSettings();
    service.refreshBriefings(settings);
    return true;
  }

  @GetMapping("briefing/{briefingId}")
  public Briefing getBriefing(@PathVariable("briefingId") String briefingId) {
    SearchmetricsSettings settings = feedbackProviderFactory.getSettings();
    service.refreshBriefing(settings, briefingId);
    return service.getBriefing(settings, briefingId);
  }
}
