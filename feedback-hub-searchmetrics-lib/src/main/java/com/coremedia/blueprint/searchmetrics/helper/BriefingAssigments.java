package com.coremedia.blueprint.searchmetrics.helper;

import com.coremedia.blueprint.searchmetrics.documents.Briefing;

import java.util.HashMap;
import java.util.Map;

/**
 * In-Memory storage for content to briefing assignments.
 * This should be replaced by a persistent storage someday.
 */
public class BriefingAssigments {
  private Map<String, Briefing> contentId2Briefing = new HashMap<>();

  public void set(String contentId, Briefing briefing) {
    contentId2Briefing.put(contentId, briefing);
  }

  public Briefing get(String id) {
    return contentId2Briefing.get(id);
  }
}
