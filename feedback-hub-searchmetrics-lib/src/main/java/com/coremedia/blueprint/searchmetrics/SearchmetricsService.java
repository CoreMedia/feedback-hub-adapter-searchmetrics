package com.coremedia.blueprint.searchmetrics;


import com.coremedia.blueprint.searchmetrics.documents.Briefing;
import com.coremedia.blueprint.searchmetrics.documents.BriefingInfo;
import com.coremedia.blueprint.searchmetrics.documents.ContentValidation;
import com.coremedia.cap.content.Content;
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
  ContentValidation validateContent(@NonNull SearchmetricsSettings settings, @NonNull String briefingId, @NonNull Content content) throws SearchmetricsException;

  void setContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId, @NonNull String briefingId);

  @Nullable
  Briefing getContentBriefing(@NonNull SearchmetricsSettings settings, @NonNull String contentId);

  void refreshBriefings(@NonNull SearchmetricsSettings settings);

  void refreshBriefing(@NonNull SearchmetricsSettings settings, @NonNull String briefingId);
}
