package com.coremedia.labs.plugins.searchmetrics.caching;

import com.coremedia.labs.plugins.searchmetrics.SearchmetricsConnector;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.labs.plugins.searchmetrics.documents.QueryResult;
import com.coremedia.labs.plugins.searchmetrics.queries.BriefingQuery;
import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class BriefingCacheKey extends CacheKey<Briefing> {
  private static final long CACHE_DURATION_MINUTES = 5;

  private SearchmetricsConnector connector;
  private SearchmetricsSettings settings;
  private String briefingId;

  public BriefingCacheKey(@NonNull SearchmetricsConnector connector,
                          @NonNull SearchmetricsSettings settings,
                          @NonNull String briefingId) {
    this.connector = connector;
    this.settings = settings;
    this.briefingId = briefingId;
  }

  @Override
  public Briefing evaluate(Cache cache) {
    Cache.cacheFor(CACHE_DURATION_MINUTES, TimeUnit.MINUTES);

    BriefingQuery query = new BriefingQuery(briefingId);
    Optional<QueryResult> queryResult = connector.postResource(settings, query.toString(), QueryResult.class);

    return queryResult.map(queryResult1 -> queryResult1.getData().getContentExperience().getBriefing()).orElse(null);
  }

  public String dependencyId() {
    return this.settings.getProjectId() + ":" + this.briefingId;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BriefingCacheKey)) {
      return false;
    }

    BriefingCacheKey that = (BriefingCacheKey) o;
    return this.dependencyId().equals(that.dependencyId());
  }

  @Override
  public int hashCode() {
    return this.briefingId.hashCode();
  }
}
