/*
 * CONFIDENTIAL
 * __________________
 *
 * [2016] All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of {The Company} and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to {The Company}
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from {The Company}.
 */
package co.iyubinest.armyofones.ui.rates;

import static co.iyubinest.armyofones.data.conversion.ConversionSource.Callback;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import co.iyubinest.armyofones.data.conversion.Conversion;
import co.iyubinest.armyofones.data.conversion.HttpConversionSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class BillsSourceShould {

  private MockWebServer server = new MockWebServer();

  private HttpConversionSource source;

  @Before
  public void before() throws Exception {
    server.start();
    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
      .baseUrl(server.url("/"))
      .build();
    source = new HttpConversionSource(retrofit);
  }

  @Test
  public void manage_failure() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(500));
    source.get(new Callback() {
      @Override
      public void onFail() {
        assertTrue(true);
      }

      @Override
      public void onSuccess(List<Conversion> bills) {
        assertTrue(false); //should fail in this way
      }
    });
    sleep(200); // sleep due to mock web server and retrofit responses
  }

  @Test
  public void manage_ok_response() throws Exception {
    server.enqueue(new MockResponse().setBody(bodyFromFile("rates.json")));
    source.get(new Callback() {
      @Override
      public void onFail() {
        assertTrue(false); //should fail in this way
      }

      @Override
      public void onSuccess(List<Conversion> bills) {
        assertEquals(4, bills.size());
      }
    });
    sleep(200); // sleep due to mock web server and retrofit responses
  }

  private String bodyFromFile(String path) throws Exception {
    StringBuilder stringBuilder = new StringBuilder("");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
      getClass().getResource("/" + path)
        .openStream()));
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
    }
    return stringBuilder.toString();
  }
}
