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

import co.iyubinest.armyofones.ui.rates.Rate;
import java.util.ArrayList;
import java.util.List;

public class RateConversion {

  private final List<Conversion> conversions;

  private final int amount;

  public RateConversion(int amount, List<Conversion> conversions) {
    this.amount = amount;
    this.conversions = conversions;
  }

  public List<Rate> billsValue() {
    List<Rate> rates = new ArrayList<>(conversions.size());
    for (Conversion conversion : conversions) {
      rates.add(map(conversion));
    }
    return rates;
  }

  private Rate map(Conversion conversion) {
    return Rate.create(conversion.currencyFrom(), convert(amount, conversion.rate()));
  }

  public static double convert(int amount, double rate) {
    if (rate < 0 || amount < 0) {
      return 0;
    }
    return amount * rate;
  }
}
