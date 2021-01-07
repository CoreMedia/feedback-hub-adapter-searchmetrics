package com.coremedia.blueprint.feedbackhub.searchmetrics.jobs;

import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.rest.cap.jobs.GenericJobErrorCode;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobContext;
import com.coremedia.rest.cap.jobs.JobExecutionException;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssignBriefingJob implements Job {
  private static final Logger LOG = LoggerFactory.getLogger(AssignBriefingJob.class);

  private final SearchmetricsService service;
  private final SitesService sitesService;

  private String siteId;
  private String briefingId;
  private String contentId;

  public AssignBriefingJob(SearchmetricsService service, SitesService sitesService) {
    this.service = service;
    this.sitesService = sitesService;
  }

  @JsonProperty("siteId")
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  @JsonProperty("briefingId")
  public void setBriefingId(String briefingId) {
    this.briefingId = briefingId;
  }

  @JsonProperty("contentId")
  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  @Nullable
  @Override
  public Object call(@NonNull JobContext jobContext) throws JobExecutionException {
    try {
      String id = contentId.substring(contentId.lastIndexOf('/') + 1);
      String capId = IdHelper.formatContentId(id);

      SearchmetricsSettings settings = getSettings(siteId);
      service.setContentBriefing(settings, capId, briefingId);
      return true;
    } catch (Exception e) {
      LOG.error("Failed to assign briefing {} to content {}: {}", briefingId, contentId, e.getMessage());
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }

  private SearchmetricsSettings getSettings(String siteId) {
    Site site = sitesService.getSite(siteId);

    throw new UnsupportedOperationException("Wait for finished BindingService!");
  }
}
