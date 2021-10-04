package com.coremedia.labs.plugins.searchmetrics.caching;

import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsConnector;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
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

    Optional<Briefing> queryResult = connector.getResource(settings, "briefs/" + briefingId, Briefing.class);
    return queryResult.orElse(null);
  }

  public String dependencyId() {
    return this.settings.getClientId() + ":" + this.briefingId;
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
