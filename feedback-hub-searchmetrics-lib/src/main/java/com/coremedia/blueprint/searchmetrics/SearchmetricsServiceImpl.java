package com.coremedia.blueprint.searchmetrics;

import com.coremedia.blueprint.searchmetrics.caching.BriefingCacheKey;
import com.coremedia.blueprint.searchmetrics.caching.BriefingInfosCacheKey;
import com.coremedia.blueprint.searchmetrics.caching.ContentValidationCacheKey;
import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.blueprint.searchmetrics.documents.BriefingInfo;
import com.coremedia.blueprint.searchmetrics.documents.ContentValidation;
import com.coremedia.blueprint.searchmetrics.helper.BriefingAssigments;
import com.coremedia.cache.Cache;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;

public class SearchmetricsServiceImpl implements SearchmetricsService {
  private SearchmetricsConnector connector;
  private BriefingAssigments briefingAssigments;
  private Cache cache;


  public SearchmetricsServiceImpl(Cache cache, SearchmetricsConnector connector, BriefingAssigments briefingAssigments) {
    this.cache = cache;
    this.connector = connector;
    this.briefingAssigments = briefingAssigments;
  }

  @NonNull
  public List<BriefingInfo> getBriefings(@NonNull SearchmetricsSettings settings) {
    return cache.get(new BriefingInfosCacheKey(connector, settings));
  }

  @NonNull
  public Briefing getBriefing(@NonNull SearchmetricsSettings settings, @NonNull String id) {
    return cache.get(new BriefingCacheKey(connector, settings, id));
  }

  @NonNull
  @Override
  public ContentValidation validate(@NonNull SearchmetricsSettings settings, @NonNull String briefingId, @NonNull String text) {
    Briefing briefing = getBriefing(settings, briefingId);
    return cache.get(new ContentValidationCacheKey(connector, settings, briefing, text));
  }

  @Override
  public void setContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId, @NonNull String briefingId) {
    Briefing briefing = getBriefing(settings, briefingId);
    briefingAssigments.set(contentId, briefing);
  }

  @Nullable
  @Override
  public Briefing getContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId) {
    Briefing briefing = briefingAssigments.get(contentId);
    if (briefing != null) {
      //when the API key has changed we want to ensure that the access to the persisted briefing is still valid
      List<BriefingInfo> briefings = getBriefings(settings);
      for (BriefingInfo briefingInfo : briefings) {
        if (briefing.getId().equals(briefingInfo.getId())) {
          return briefing;
        }
      }
    }

    return null;
  }

  @Override
  public void refreshBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId) {
    cache.invalidate(new BriefingCacheKey(connector, settings, briefingId).dependencyId());
  }

  @Override
  public List<BriefingInfo> refreshBriefings(@NonNull SearchmetricsSettings settings) {
    cache.invalidate(new BriefingInfosCacheKey(connector, settings).dependencyId());

    List<BriefingInfo> briefings = getBriefings(settings);
    for (BriefingInfo briefing : briefings) {
      cache.invalidate(new BriefingCacheKey(connector, settings, briefing.getId()).dependencyId());
    }

    return getBriefings(settings);
  }
}
