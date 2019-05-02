package co.iyubinest.armyofones.ui.rates;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.iyubinest.armyofones.R;
import co.iyubinest.armyofones.data.conversion.ConversionSource;
import co.iyubinest.armyofones.ui.ArmyActivity;
import co.iyubinest.armyofones.ui.widgets.RatesWidget;
import java.util.List;
import javax.inject.Inject;

public class RatesActivity extends ArmyActivity implements RatesView {

  @Inject
  ConversionSource source;

  @BindView(R.id.loading)
  View loadingView;

  @BindView(R.id.retry)
  View retryView;

  @BindView(R.id.rates_wrapper)
  View ratesWrapper;

  @BindView(R.id.rates)
  RatesWidget ratesView;

  @BindView(R.id.bill_field)
  EditText billField;

  private Rates rates;

  private TextWatcher billFieldCallback = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      if (billField.getText()
        .length() == 0) {
        billField.setText("1");
        return;
      }
      rates.type(Integer.valueOf(billField.getText()
        .toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
  };

  @OnClick(R.id.retry_button)
  public void retry() {
    rates.get();
  }

  @Override
  public void showBills(List<Rate> rates) {
    show(ratesWrapper);
    ratesView.add(rates);
    billField.addTextChangedListener(billFieldCallback);
  }

  @Override
  public void showLoading() {
    show(loadingView);
  }

  @Override
  public void showRetryView() {
    show(retryView);
  }

  private void show(View view) {
    loadingView.setVisibility(GONE);
    retryView.setVisibility(GONE);
    ratesWrapper.setVisibility(GONE);
    view.setVisibility(VISIBLE);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rates);
    injector().inject(this);
    ButterKnife.bind(this);
    rates = new Rates(this, source);
    rates.get();
  }
}
