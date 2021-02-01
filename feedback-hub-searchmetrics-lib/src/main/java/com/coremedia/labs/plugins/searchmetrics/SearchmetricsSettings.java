package com.coremedia.labs.plugins.searchmetrics;

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
