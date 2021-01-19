package com.coremedia.blueprint.feedbackhub.searchmetrics.jobs;

import com.coremedia.blueprint.feedbackhub.searchmetrics.FeedbackSettingsProvider;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.rest.cap.jobs.GenericJobErrorCode;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobContext;
import com.coremedia.rest.cap.jobs.JobExecutionException;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetBriefingDetailsJob implements Job {
  private static final Logger LOG = LoggerFactory.getLogger(GetBriefingDetailsJob.class);

  private final FeedbackSettingsProvider feedbackSettingsProvider;
  private final SearchmetricsService service;

  private String siteId;
  private String groupId;
  private String briefingId;

  public GetBriefingDetailsJob(@NonNull FeedbackSettingsProvider feedbackSettingsProvider, SearchmetricsService service) {
    this.feedbackSettingsProvider = feedbackSettingsProvider;
    this.service = service;
  }

  @JsonProperty("siteId")
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  @JsonProperty("groupId")
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }


  @JsonProperty("briefingId")
  public void setBriefingId(String briefingId) {
    this.briefingId = briefingId;
  }

  @Nullable
  @Override
  public Object call(@NonNull JobContext jobContext) throws JobExecutionException {
    try {
      SearchmetricsSettings settings = getSettings();
      service.refreshBriefing(settings, briefingId);
      return service.getBriefing(settings, briefingId);
    } catch (Exception e) {
      LOG.error("Failed to get briefing details for briefing " + briefingId + ": " + e.getMessage(), e);
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }

  private SearchmetricsSettings getSettings() {
    return feedbackSettingsProvider.getSettings(groupId, siteId);
  }
}
