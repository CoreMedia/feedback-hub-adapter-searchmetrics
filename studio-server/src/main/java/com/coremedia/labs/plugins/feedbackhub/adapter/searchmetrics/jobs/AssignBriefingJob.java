package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs;

import com.coremedia.cap.common.IdHelper;
import com.coremedia.feedbackhub.settings.FeedbackSettingsProvider;
import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.SearchmetricsFeedbackAdapterFactory;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.rest.cap.jobs.GenericJobErrorCode;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobContext;
import com.coremedia.rest.cap.jobs.JobExecutionException;
import com.google.gson.annotations.SerializedName;
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

  @SerializedName("siteId")
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  @SerializedName("groupId")
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  @SerializedName("briefingId")
  public void setBriefingId(String briefingId) {
    this.briefingId = briefingId;
  }

  @SerializedName("contentId")
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
    return feedbackSettingsProvider.getSettings(SearchmetricsSettings.class, SearchmetricsFeedbackAdapterFactory.TYPE, groupId, siteId);
  }
}
