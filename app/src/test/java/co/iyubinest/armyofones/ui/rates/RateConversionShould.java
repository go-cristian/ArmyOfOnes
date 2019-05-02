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

import static co.iyubinest.armyofones.data.conversion.RateConversion.convert;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RateConversionShould {

  @Test
  public void convert_to_right_amount_of_bills() throws Exception {
    assertThat(convert(2, 0.02), is(0.04));
    assertThat(convert(1, 1.89), is(1.89));
    assertThat(convert(3, 3), is(9.0));
  }
}
