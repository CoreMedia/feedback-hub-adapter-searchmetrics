package com.coremedia.blueprint.feedbackhub.searchmetrics.jobs;

import com.coremedia.blueprint.feedbackhub.searchmetrics.FeedbackSettingsProvider;
import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class GetBriefingsJobFactory implements JobFactory {

  private SearchmetricsService service;
  private FeedbackSettingsProvider feedbackSettingsProvider;

  public GetBriefingsJobFactory(SearchmetricsService service, FeedbackSettingsProvider feedbackSettingsProvider) {
    this.service = service;
    this.feedbackSettingsProvider = feedbackSettingsProvider;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("getBriefings");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new GetBriefingsJob(service,  feedbackSettingsProvider);
  }
}
