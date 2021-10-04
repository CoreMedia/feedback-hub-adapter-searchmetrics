package com.coremedia.labs.plugins.searchmetrics;

import com.coremedia.cache.Cache;
import com.coremedia.labs.plugins.searchmetrics.caching.BriefingCacheKey;
import com.coremedia.labs.plugins.searchmetrics.caching.BriefingsCacheKey;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.labs.plugins.searchmetrics.documents.ContentUpdate;
import com.coremedia.labs.plugins.searchmetrics.helper.BriefingAssigments;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SearchmetricsServiceImpl implements SearchmetricsService {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsServiceImpl.class);

  private SearchmetricsConnector connector;
  private BriefingAssigments briefingAssigments;
  private Cache cache;
  private ObjectMapper objectMapper;


  public SearchmetricsServiceImpl(Cache cache, SearchmetricsConnector connector, BriefingAssigments briefingAssigments) {
    this.cache = cache;
    this.connector = connector;
    this.briefingAssigments = briefingAssigments;

    this.objectMapper = new ObjectMapper();
  }

  @NonNull
  public List<Briefing> getBriefings(@NonNull SearchmetricsSettings settings, boolean invalidate) {
    if(invalidate) {
      cache.invalidate(new BriefingsCacheKey(connector, settings).dependencyId());
    }
    return cache.get(new BriefingsCacheKey(connector, settings));
  }

  @NonNull
  public Briefing getBriefing(@NonNull SearchmetricsSettings settings, @NonNull String id) {
    return cache.get(new BriefingCacheKey(connector, settings, id));
  }

  @NonNull
  @Override
  public Briefing updateBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId, @NonNull String text) {
    Briefing briefing = getBriefing(settings, briefingId);

    ContentUpdate update = new ContentUpdate();
    update.setContent(text);
    update.setTitle(briefing.getBriefName());
    update.setMetaTitle("Update from CoreMedia Studio");
    update.setMetaDescription("");

    try {
      String valueAsString = objectMapper.writeValueAsString(update);
      connector.postResource(settings, "brief/" + briefingId + "/content-versions", valueAsString, String.class);
    }
    catch (Exception e) {
      LOG.error("Failed to update content: " + e.getMessage(), e);
    }

    return refreshBriefing(settings, briefingId);
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
      List<Briefing> briefings = getBriefings(settings, false);
      for (Briefing briefingInfo : briefings) {
        if (briefing.getBriefId().equals(briefingInfo.getBriefId())) {
          return briefing;
        }
      }
    }

    return null;
  }

  @Override
  public Briefing refreshBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId) {
    cache.invalidate(new BriefingCacheKey(connector, settings, briefingId).dependencyId());
    return getBriefing(settings, briefingId);
  }
}
