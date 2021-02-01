package com.coremedia.labs.plugins.searchmetrics;


import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.labs.plugins.searchmetrics.documents.BriefingInfo;
import com.coremedia.labs.plugins.searchmetrics.documents.ContentValidation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;

/**
 *
 */
public interface SearchmetricsService {
  @NonNull
  List<BriefingInfo> getBriefings(@NonNull SearchmetricsSettings settings) throws SearchmetricsException;

  @NonNull
  Briefing getBriefing(@NonNull SearchmetricsSettings settings, @NonNull String id) throws SearchmetricsException;

  @NonNull
  ContentValidation validate(@NonNull SearchmetricsSettings settings, @NonNull String briefingId, @NonNull String text) throws SearchmetricsException;

  void setContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId, @NonNull String briefingId);

  @Nullable
  Briefing getContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId);

  @NonNull
  List<BriefingInfo> refreshBriefings(@NonNull SearchmetricsSettings settings);

  void refreshBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId);
}
