package com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.jobs;

import com.coremedia.labs.plugins.feedbackhub.adapter.searchmetrics.SearchmetricsSettingsProvider;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class GetBriefingsJobFactory implements JobFactory {

  private SearchmetricsService service;
  private SearchmetricsSettingsProvider feedbackSettingsProvider;

  public GetBriefingsJobFactory(SearchmetricsService service, SearchmetricsSettingsProvider feedbackSettingsProvider) {
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
