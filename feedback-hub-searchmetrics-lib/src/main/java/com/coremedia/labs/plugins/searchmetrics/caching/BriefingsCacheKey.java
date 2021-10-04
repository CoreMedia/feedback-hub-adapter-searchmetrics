package com.coremedia.labs.plugins.searchmetrics.caching;

import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsConnector;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefings;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class BriefingsCacheKey extends CacheKey<List<Briefing>> {

  private final SearchmetricsConnector connector;
  private final SearchmetricsSettings settings;

  public BriefingsCacheKey(@NonNull SearchmetricsConnector connector,
                           @NonNull SearchmetricsSettings settings) {
    this.connector = connector;
    this.settings = settings;
  }

  @Override
  public List<Briefing> evaluate(Cache cache) {
    Cache.dependencyOn(dependencyId());
    Optional<Briefings> queryResult = connector.getResource(settings, "briefs", Briefings.class);
    if (queryResult.isPresent()) {
      return queryResult.get().getData();
    }
    return Collections.emptyList();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BriefingsCacheKey)) {
      return false;
    }

    BriefingsCacheKey that = (BriefingsCacheKey) o;
    return this.dependencyId().equals(that.dependencyId());
  }

  public String dependencyId() {
    return this.settings.getClientId();
  }

  @Override
  public int hashCode() {
    return this.settings.getClientId().hashCode() + this.settings.getClientSecret().hashCode();
  }
}
