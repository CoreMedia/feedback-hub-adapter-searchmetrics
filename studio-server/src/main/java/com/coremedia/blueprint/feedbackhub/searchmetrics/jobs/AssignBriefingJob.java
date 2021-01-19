package com.coremedia.blueprint.feedbackhub.searchmetrics.jobs;

import com.coremedia.blueprint.feedbackhub.searchmetrics.FeedbackSettingsProvider;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.cap.common.IdHelper;
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
  private final FeedbackSettingsProvider feedbackSettingsProvider;

  private String siteId;
  private String briefingId;
  private String contentId;
  private String groupId;

  public AssignBriefingJob(SearchmetricsService service, FeedbackSettingsProvider feedbackSettingsProvider) {
    this.service = service;
    this.feedbackSettingsProvider = feedbackSettingsProvider;
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

      SearchmetricsSettings settings = getSettings();
      service.setContentBriefing(settings, capId, briefingId);
      return true;
    } catch (Exception e) {
      LOG.error("Failed to assign briefing {} to content {}: {}", briefingId, contentId, e.getMessage());
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }


  private SearchmetricsSettings getSettings() {
    return feedbackSettingsProvider.getSettings(groupId, siteId);
  }
}
