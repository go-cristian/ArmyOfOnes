package co.iyubinest.armyofones;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import co.iyubinest.armyofones.ui.rates.RatesActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RatesActivityShould {

  @Rule
  public ActivityTestRule<RatesActivity> rule = new ActivityTestRule<>(RatesActivity.class);

  @Test
  public void show_fail_on_error() throws Exception {
    rule.getActivity();
    onView(withId(R.id.retry)).check(matches(withEffectiveVisibility(GONE)));
  }

  @Test
  public void show_rates_widget_on_load() throws Exception {
    rule.getActivity();
    onView(withId(R.id.rates)).check(matches(withChildNumber(4)));
    onView(withId(R.id.bill_field)).perform(replaceText("2"));
    onView(withId(R.id.rates)).check(matches(withChildNumber(4)));
  }

  public static Matcher<? super View> withChildNumber(final int childCOunt) {
    return new BoundedMatcher<Object, RecyclerView>(RecyclerView.class) {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child number:");
      }

      @Override
      protected boolean matchesSafely(RecyclerView view) {
        return view.getAdapter()
          .getItemCount() == childCOunt;
      }
    };
  }
}