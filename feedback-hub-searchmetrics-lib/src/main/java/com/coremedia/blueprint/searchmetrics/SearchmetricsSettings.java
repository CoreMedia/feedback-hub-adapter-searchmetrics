package com.coremedia.blueprint.searchmetrics;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 *
 */
public interface SearchmetricsSettings {

  @NonNull
  String getSharedSecret();

  @NonNull
  String getApiKey();

  @NonNull
  String getPropertyName();

  @NonNull
  Integer getProjectId();

  @Nullable
  Boolean getIncludeKeywords();

  @Nullable
  Boolean getIncludeTaxonomies();
}
