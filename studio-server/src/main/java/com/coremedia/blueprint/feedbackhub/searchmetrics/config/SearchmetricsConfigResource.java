package com.coremedia.blueprint.feedbackhub.searchmetrics.config;

import com.coremedia.blueprint.feedbackhub.searchmetrics.SearchmetricsFeedbackAdapterFactory;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.blueprint.searchmetrics.documents.BriefingInfo;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.feedbackhub.FeedbackService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping(value = "searchmetrics/{siteId}", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchmetricsConfigResource {

  private final SearchmetricsService service;
  private final FeedbackService feedbackService;
  private final SitesService sitesService;

  public SearchmetricsConfigResource(SearchmetricsService searchmetricsService, FeedbackService feedbackService, SitesService sitesService) {
    service = searchmetricsService;
    this.feedbackService = feedbackService;
    this.sitesService = sitesService;
  }

  @PostMapping("briefingmapping")
  public void setBriefingMapping(@PathVariable("siteId") String siteId,
                                 @RequestParam("contentId") String contentId,
                                 @RequestParam("briefingId") String briefingId) {
    String id = contentId.substring(contentId.lastIndexOf('/') + 1);
    String capId = IdHelper.formatContentId(id);

    SearchmetricsSettings settings = getSettings(siteId);
    service.setContentBriefing(settings, capId, briefingId);
  }

  @GetMapping("briefings/refresh")
  public List<BriefingInfo> refreshBriefings(@PathVariable("siteId") String siteId) {
    SearchmetricsSettings settings = getSettings(siteId);
    return service.refreshBriefings(settings);
  }

  @GetMapping("briefing/{briefingId}")
  public Briefing getBriefing(@PathVariable("siteId") String siteId,
                              @PathVariable("briefingId") String briefingId) {
    SearchmetricsSettings settings = getSettings(siteId);
    service.refreshBriefing(settings, briefingId);
    return service.getBriefing(settings, briefingId);
  }

  private SearchmetricsSettings getSettings(String siteId) {
    Site site = sitesService.getSite(siteId);
    SearchmetricsSettings settings = feedbackService.getSettings(SearchmetricsFeedbackAdapterFactory.TYPE, SearchmetricsSettings.class, site);
    if(settings == null) {
      settings = feedbackService.getSettings(SearchmetricsFeedbackAdapterFactory.TYPE, SearchmetricsSettings.class);
    }
    return settings;
  }
}
