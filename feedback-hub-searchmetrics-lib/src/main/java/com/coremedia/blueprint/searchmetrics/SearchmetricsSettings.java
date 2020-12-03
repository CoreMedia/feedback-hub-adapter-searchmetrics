package com.coremedia.blueprint.searchmetrics;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 *
 */
public interface SearchmetricsSettings {

  @NonNull
  String getSharedSecret();

  @NonNull
  String getApiKey();

  @NonNull
  Integer getProjectId();
}
