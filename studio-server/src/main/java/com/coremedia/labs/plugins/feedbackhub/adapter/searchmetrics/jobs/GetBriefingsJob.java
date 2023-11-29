package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs;

import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.SearchmetricsSettingsProvider;
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

public class GetBriefingsJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(GetBriefingsJob.class);

    private final SearchmetricsService service;
    private final SearchmetricsSettingsProvider feedbackSettingsProvider;

    private String siteId;
    private String groupId;

    public GetBriefingsJob(SearchmetricsService service, SearchmetricsSettingsProvider feedbackSettingsProvider) {
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
