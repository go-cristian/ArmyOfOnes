package co.iyubinest.armyofones.data.conversion.rates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HttpRates {

  @SerializedName("base")
  @Expose
  public String base;

  @SerializedName("date")
  @Expose
  public String date;

  @SerializedName("rates")
  @Expose
  public Rates rates;
}
