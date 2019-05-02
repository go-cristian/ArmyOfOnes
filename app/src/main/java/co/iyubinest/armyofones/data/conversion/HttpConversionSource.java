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
package co.iyubinest.armyofones.data.conversion;

import co.iyubinest.armyofones.data.conversion.rates.HttpRates;
import co.iyubinest.armyofones.data.conversion.rates.RateService;
import co.iyubinest.armyofones.data.conversion.rates.Rates;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HttpConversionSource implements ConversionSource {

  private static final String GBP = "GBP";

  private static final String EUR = "EUR";

  private static final String JPY = "JPY";

  private static final String BRL = "BRL";

  private static final String[] RATES = {GBP, EUR, JPY, BRL};

  private RateService service;

  public HttpConversionSource(Retrofit retrofit) {
    service = retrofit.create(RateService.class);
  }

  @Override
  public void get(final Callback callback) {
    service.get()
      .enqueue(new retrofit2.Callback<HttpRates>() {
        @Override
        public void onResponse(Call<HttpRates> call, Response<HttpRates> response) {
          if (response.isSuccessful()) {
            callback.onSuccess(map(response));
          } else {
            callback.onFail();
          }
        }

        @Override
        public void onFailure(Call<HttpRates> call, Throwable t) {
          callback.onFail();
        }
      });
  }

  private List<Conversion> map(Response<HttpRates> response) {
    try {
      HttpRates body = response.body();
      if (body == null) {
        throw new IllegalArgumentException();
      }
      return buildConversions(body);
    } catch (Exception ignored) {
      throw new IllegalArgumentException();
    }
  }

  private List<Conversion> buildConversions(HttpRates body)
    throws IllegalAccessException, NoSuchFieldException {
    List<Conversion> bills = new ArrayList<>(RATES.length);
    Rates bodyRates = body.rates;
    for (String rateName : RATES) {
      Double value = (Double) bodyRates.getClass()
        .getField(rateName)
        .get(bodyRates);
      bills.add(map(rateName, value));
    }
    return bills;
  }

  private Conversion map(String name, double rate) {
    return Conversion.create(name, rate);
  }
}
