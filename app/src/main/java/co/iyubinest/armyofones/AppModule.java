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
package co.iyubinest.armyofones;

import co.iyubinest.armyofones.data.conversion.ConversionSource;
import co.iyubinest.armyofones.data.conversion.HttpConversionSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Module
public class AppModule {

  @Provides
  @Singleton
  public ConversionSource ratesSource(Retrofit retrofit) {
    return new HttpConversionSource(retrofit);
  }

  @Provides
  @Singleton
  Retrofit retrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
    return retrofit;
  }
}
