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
package co.iyubinest.armyofones.ui.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import co.iyubinest.armyofones.R;
import co.iyubinest.armyofones.ui.rates.Rate;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RatesWidget extends RecyclerView {

  private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

  public RatesWidget(Context context) {
    super(context);
    init();
  }

  private void init() {
    decimalFormat.setRoundingMode(RoundingMode.DOWN);
    setLayoutManager(new GridLayoutManager(getContext(), 2));
    setAdapter(new Adapter());
  }

  public RatesWidget(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public RatesWidget(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public void add(List<Rate> rates) {
    ((Adapter) getAdapter()).add(rates);
  }

  private class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private double maxValue = 0;

    private List<Rate> data = new ArrayList<>();

    public void add(List<Rate> rates) {
      maxValue = 0;
      for (Rate rate : rates) {
        if (rate.value() > maxValue) {
          maxValue = rate.value();
        }
      }
      data.clear();
      data.addAll(rates);
      notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(getContext())
        .inflate(R.layout.rates, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.bill(maxValue, data.get(position));
    }

    @Override
    public int getItemCount() {
      return data.size();
    }
  }

  private class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView valueView;

    private final TextView currencyNameView;

    private final TextView barView;

    public ViewHolder(View itemView) {
      super(itemView);
      valueView = (TextView) itemView.findViewById(R.id.bill_value);
      currencyNameView = (TextView) itemView.findViewById(R.id.bill_currency);
      barView = (TextView) itemView.findViewById(R.id.bill_bar);
    }

    public void bill(double maxValue, Rate rate) {
      valueView.setText(decimalFormat.format(rate.value()));
      currencyNameView.setText(rate.currency());
      float finalScale;
      if (maxValue == 0) {
        finalScale = 0;
      } else {
        finalScale = (float) (rate.value() / maxValue);
      }
      animateViewBar(barView, finalScale);
    }

    private void animateViewBar(View view, float finalScale) {
      view.setPivotX(1);
      view.setPivotY(0);
      ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0, finalScale);
      objectAnimator.setDuration(2500);
      objectAnimator.start();
    }
  }
}
