package com.coremedia.labs.plugins.searchmetrics.caching;

import com.coremedia.labs.plugins.searchmetrics.SearchmetricsConnector;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.labs.plugins.searchmetrics.documents.BriefingInfo;
import com.coremedia.labs.plugins.searchmetrics.documents.QueryResult;
import com.coremedia.labs.plugins.searchmetrics.queries.BriefingsQuery;
import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class BriefingInfosCacheKey extends CacheKey<List<BriefingInfo>> {

  private SearchmetricsConnector connector;
  private SearchmetricsSettings settings;

  public BriefingInfosCacheKey(@NonNull SearchmetricsConnector connector,
                               @NonNull SearchmetricsSettings settings) {
    this.connector = connector;
    this.settings = settings;
  }

  @Override
  public List<BriefingInfo> evaluate(Cache cache) {
    Cache.dependencyOn(dependencyId());
    BriefingsQuery query = new BriefingsQuery(settings.getProjectId());
    Optional<QueryResult> queryResult = connector.postResource(settings, query.toString(), QueryResult.class);
    if (queryResult.isPresent()) {
      return queryResult.get().getData().getContentExperience().getBriefingsList().getBriefings();
    }
    return Collections.emptyList();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BriefingInfosCacheKey)) {
      return false;
    }

    BriefingInfosCacheKey that = (BriefingInfosCacheKey) o;
    return this.dependencyId().equals(that.dependencyId());
  }

  public String dependencyId() {
    return this.settings.getProjectId() + ":" + this.settings.getApiKey();
  }

  @Override
  public int hashCode() {
    return this.settings.getApiKey().hashCode();
  }
}
