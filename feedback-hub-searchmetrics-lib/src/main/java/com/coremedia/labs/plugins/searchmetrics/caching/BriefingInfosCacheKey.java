package com.coremedia.labs.plugins.searchmetrics.caching;

import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsConnector;
import com.coremedia.labs.plugins.searchmetrics.SearchmetricsSettings;
import com.coremedia.labs.plugins.searchmetrics.documents.BriefingInfo;
import com.coremedia.labs.plugins.searchmetrics.documents.BriefingList;
import com.coremedia.labs.plugins.searchmetrics.documents.ContentExperience;
import com.coremedia.labs.plugins.searchmetrics.documents.QueryData;
import com.coremedia.labs.plugins.searchmetrics.documents.QueryResult;
import com.coremedia.labs.plugins.searchmetrics.queries.BriefingsQuery;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class BriefingInfosCacheKey extends CacheKey<List<BriefingInfo>> {
  private static Logger LOG = LoggerFactory.getLogger(BriefingInfosCacheKey.class);

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
      QueryData data = queryResult.get().getData();
      ContentExperience contentExperience = data.getContentExperience();
      if (contentExperience != null) {
        BriefingList briefingsList = contentExperience.getBriefingsList();
        List<BriefingInfo> briefings = briefingsList.getBriefings();
        LOG.info("Searchmetrics returned " + briefings.size() + " briefings.");
        return briefings;
      } else {
        LOG.warn("Failed to load briefings from Searchmetrics, view LoggingInterceptor debug logs for details.");
      }
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
