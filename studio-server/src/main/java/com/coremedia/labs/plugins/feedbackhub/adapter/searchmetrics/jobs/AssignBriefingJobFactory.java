package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs;

import com.coremedia.feedbackhub.settings.FeedbackSettingsProvider;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class AssignBriefingJobFactory implements JobFactory {

  private SearchmetricsService service;
  private FeedbackSettingsProvider feedbackSettingsProvider;

  public AssignBriefingJobFactory(SearchmetricsService service, FeedbackSettingsProvider feedbackSettingsProvider) {
    this.service = service;
    this.feedbackSettingsProvider = feedbackSettingsProvider;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("assignBriefing");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new AssignBriefingJob(service,  feedbackSettingsProvider);
  }
}
