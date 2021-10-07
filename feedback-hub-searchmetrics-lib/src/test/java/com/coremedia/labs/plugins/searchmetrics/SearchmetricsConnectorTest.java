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

  private static final String CLIENT_ID = "qf2x5FufynFcNUmMRznqJGFqrZsNRaez";
  private static final String CLIENT_SECRET = "L7Ys2JnakHeSC8egkHEUsSTksHtCudj3iFZP_DOQpavLryDkJNx_dLwCl1BzFj03";
  private static final String REDIRECT_URL = "https://www.coremedia.com/";

  private SearchmetricsSettings settings;
  private SearchmetricsService searchmetricsService;

  @Before
  public void setup() {
    settings = new SearchmetricsSettings() {
      @NonNull
      @Override
      public String getClientId() {
        return CLIENT_ID;
      }

      @NonNull
      @Override
      public String getClientSecret() {
        return CLIENT_SECRET;
      }

      @NonNull
      @Override
      public String getRedirectUrl() {
        return REDIRECT_URL;
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
