package co.iyubinest.armyofones.ui.rates;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import co.iyubinest.armyofones.data.conversion.Conversion;
import co.iyubinest.armyofones.data.conversion.ConversionSource;
import co.iyubinest.armyofones.data.conversion.ConversionSource.Callback;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

public class BillsShould {

  @Mock
  RatesView view;

  @Mock
  ConversionSource source;

  @Captor
  ArgumentCaptor<Callback> captor;

  private Rates rates;

  @Before
  public void before() {
    initMocks(this);
    rates = new Rates(view, source);
  }

  @Test
  public void show_fail_view_when_requesting_data_and_error_on_source() throws Exception {
    rates.get();
    //manually mock the callback
    verify(source, times(1)).get(captor.capture());
    captor.getValue()
      .onFail();
    verify(view, times(1)).showRetryView();
  }

  @Test
  public void show_fail_view_when_requesting_data_and_response_is_zero_length() throws Exception {
    rates.get();
    //manually mock the callback
    List<Conversion> response = mock(List.class);
    when(response.size()).thenReturn(0);
    verify(source, times(1)).get(captor.capture());
    captor.getValue()
      .onSuccess(response);
    verify(view, times(1)).showRetryView();
  }

  @Test
  public void show_loading_view_on_start() throws Exception {
    rates.get();
    verify(view, times(1)).showLoading();
  }

  @Test
  public void show_ui_when_data_ready() throws Exception {
    rates.get();
    //manually mock the callback
    List<Conversion> response = new ArrayList<>();
    response.add(Conversion.create("", 0d));
    verify(source, times(1)).get(captor.capture());
    captor.getValue()
      .onSuccess(response);
    verify(view, times(1)).showBills(anyList());
  }

  @Test
  public void update_ui_on_type() {
    //prepare data
    rates.get();
    List<Conversion> response = new ArrayList<>();
    response.add(Conversion.create("GBP", 0.2));
    verify(source, times(1)).get(captor.capture());
    captor.getValue()
      .onSuccess(response);
    //act
    rates.type(2);
    verify(view, times(2)).showBills(anyList());
  }
}