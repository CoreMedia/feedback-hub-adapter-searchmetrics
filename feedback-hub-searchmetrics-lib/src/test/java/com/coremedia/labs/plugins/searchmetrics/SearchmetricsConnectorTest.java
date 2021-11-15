package com.coremedia.labs.plugins.searchmetrics;

import com.coremedia.cache.Cache;
import com.coremedia.labs.plugins.searchmetrics.documents.Briefing;
import com.coremedia.labs.plugins.searchmetrics.helper.BriefingAssigments;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SearchmetricsConnectorTest {

  private static final String API_KEY = "8f4a831e14682349b25936ef26f765c142cba537";
  private static final String API_SECRET = "1420f7e88883b58a4e5d108c77e6520a9d986b4d";

  private SearchmetricsSettings settings;
  private SearchmetricsService searchmetricsService;

  @Before
  public void setup() {
    settings = new SearchmetricsSettings() {
      @NonNull
      @Override
      public String getApiKey() {
        return API_KEY;
      }

      @NonNull
      @Override
      public String getApiSecret() {
        return API_SECRET;
      }
    };

    SearchmetricsConnector connector = new SearchmetricsConnector(new RestTemplate());
    searchmetricsService = new SearchmetricsServiceImpl(new Cache("test"), connector, new BriefingAssigments());
  }

  @Test
  public void testGetBriefings() {
    List<Briefing> briefings = searchmetricsService.getBriefings(settings, false);
    Assert.assertNotEquals(0 , briefings.size());
  }
}
