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

import co.iyubinest.armyofones.data.conversion.Conversion;
import co.iyubinest.armyofones.data.conversion.ConversionSource;
import co.iyubinest.armyofones.data.conversion.RateConversion;
import java.util.List;

public class Rates {

  private final RatesView view;

  private final ConversionSource source;

  private List<Conversion> cache;

  private final ConversionSource.Callback callback = new ConversionSource.Callback() {
    @Override
    public void onFail() {
      view.showRetryView();
    }

    @Override
    public void onSuccess(List<Conversion> conversions) {
      if (conversions.size() == 0) {
        view.showRetryView();
      } else {
        cache = conversions;
        view.showBills(new RateConversion(1, conversions).billsValue());
      }
    }
  };

  public Rates(RatesView view, ConversionSource source) {
    this.view = view;
    this.source = source;
  }

  public void get() {
    view.showLoading();
    source.get(callback);
  }

  public void type(int amountTyped) {
    view.showBills(new RateConversion(amountTyped, cache).billsValue());
  }
}
