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

import android.content.Context;
import android.util.AttributeSet;

public class SquareTextView extends android.support.v7.widget.AppCompatTextView {

  public SquareTextView(Context context) {
    super(context);
  }

  public SquareTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
  }
}
