package com.coremedia.labs.plugins.searchmetrics;

import com.coremedia.labs.plugins.searchmetrics.documents.QueryResult;
import com.coremedia.labs.plugins.searchmetrics.queries.BriefingsQuery;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */
public class SearchmetricsConnectorTest {
  private static final Logger LOG = LoggerFactory.getLogger(SearchmetricsConnectorTest.class);

  @Test
  public void testSearchMetrics() {
    if (System.getenv("sm.sharedSecret") == null) {
      LOG.warn("Test ignored, pass env properties.");
      return;
    }

    SearchmetricsServiceConfiguration config = new SearchmetricsServiceConfiguration();
    RestTemplate restTemplate = config.searchmetricsRestTemplate();
    SearchmetricsSettings settings = new SearchmetricsSettings() {
      @NonNull
      @Override
      public String getSharedSecret() {
        return System.getenv("sm.sharedSecret");
      }

      @NonNull
      @Override
      public String getApiKey() {
        return System.getenv("sm.apiKey");
      }

      @NonNull
      @Override
      public Integer getProjectId() {
        return Integer.parseInt(System.getenv("sm.projectId"));
      }
    };

    SearchmetricsConnector connector = new SearchmetricsConnector(restTemplate);
    BriefingsQuery query = new BriefingsQuery(settings.getProjectId());
    Optional<QueryResult> queryData = connector.postResource(settings, query.toString(), QueryResult.class);
    QueryResult queryResult = queryData.get();
    assertNotNull(queryResult);
    assertNotNull(queryResult.getData());
    assertNotNull(queryResult.getData().getContentExperience());
  }
}
