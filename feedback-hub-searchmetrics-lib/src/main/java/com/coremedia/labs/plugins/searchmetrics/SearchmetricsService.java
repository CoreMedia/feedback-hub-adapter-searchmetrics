package com.coremedia.labs.plugins.searchmetrics;


import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;

/**
 *
 */
public interface SearchmetricsService {
  @NonNull
  List<Briefing> getBriefings(@NonNull SearchmetricsSettings settings, boolean invalidate) throws SearchmetricsException;

  @NonNull
  Briefing getBriefing(@NonNull SearchmetricsSettings settings, @NonNull String id) throws SearchmetricsException;

  @NonNull
  Briefing updateBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId, @NonNull String text) throws SearchmetricsException;

  void setContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId, @NonNull String briefingId);

  @Nullable
  Briefing getContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId);

  @NonNull
  Briefing refreshBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId);
}
