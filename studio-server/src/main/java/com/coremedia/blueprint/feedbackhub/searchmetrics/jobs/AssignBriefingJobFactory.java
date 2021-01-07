package com.coremedia.blueprint.feedbackhub.searchmetrics.jobs;

import com.coremedia.blueprint.searchmetrics.SearchmetricsService;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.rest.cap.jobs.Job;
import com.coremedia.rest.cap.jobs.JobFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

public class AssignBriefingJobFactory implements JobFactory {

  private SearchmetricsService service;
  private SitesService sitesService;

  public AssignBriefingJobFactory(SearchmetricsService service, SitesService sitesService) {
    this.service = service;
    this.sitesService = sitesService;
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("assignBriefing");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new AssignBriefingJob(service,  sitesService);
  }
}
