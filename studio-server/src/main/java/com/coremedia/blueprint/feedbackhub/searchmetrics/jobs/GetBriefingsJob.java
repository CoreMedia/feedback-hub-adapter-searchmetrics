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

public class GetBriefingsJob implements Job {
  private static final Logger LOG = LoggerFactory.getLogger(GetBriefingsJob.class);

  private final SearchmetricsService service;
  private final FeedbackSettingsProvider feedbackSettingsProvider;

  private String siteId;
  private String groupId;

  public GetBriefingsJob(SearchmetricsService service, FeedbackSettingsProvider feedbackSettingsProvider) {
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

  @Nullable
  @Override
  public Object call(@NonNull JobContext jobContext) throws JobExecutionException {
    try {
      SearchmetricsSettings settings = getSettings();
      return service.refreshBriefings(settings);
    } catch (Exception e) {
      LOG.error("Failed to get briefings for site {}: {}", siteId, e.getMessage());
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }

  private SearchmetricsSettings getSettings() {
    return feedbackSettingsProvider.getSettings(groupId, siteId);
  }
}
