package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs;

import com.coremedia.feedbackhub.settings.FeedbackSettingsProvider;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class GetBriefingDetailsJobFactory implements JobFactory {

  private FeedbackSettingsProvider feedbackSettingsProvider;
  private SearchmetricsService service;

  public GetBriefingDetailsJobFactory(@NonNull FeedbackSettingsProvider feedbackSettingsProvider, SearchmetricsService service) {
    this.feedbackSettingsProvider = feedbackSettingsProvider;
    this.service = service;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("getBriefingDetails");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new GetBriefingDetailsJob(feedbackSettingsProvider, service);
  }
}
