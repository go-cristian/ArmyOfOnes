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

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Conversion {

  public static Conversion create(String currencyFrom, double rate) {
    return new AutoValue_Conversion(currencyFrom, rate);
  }

  public abstract String currencyFrom();

  public abstract double rate();
}
