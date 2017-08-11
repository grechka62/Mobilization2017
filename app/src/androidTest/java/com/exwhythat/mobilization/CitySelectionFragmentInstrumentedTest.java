package com.exwhythat.mobilization;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.AppCompatImageButton;

import com.exwhythat.mobilization.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by Grechka on 29.07.2017.
 */

public class CitySelectionFragmentInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void goToCitySelection() {
        onView(withClassName(equalTo(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.action_add_city)).perform(click());
        onView(withId(R.id.edit_city)).perform(typeText("k"));
        SystemClock.sleep(400);
    }

    @Test
    public void citySelection_showLoading() {
        onView(withId(R.id.loading_suggest)).check(matches(isDisplayed()));
        onView(withId(R.id.city_input_hint)).check(matches(not(isDisplayed())));
        onView(withId(R.id.suggest_recycler)).check(matches(not(isDisplayed())));
    }

    @Test
    public void citySelection_showSuggest() {
        SystemClock.sleep(1000);

        onView(withId(R.id.loading_suggest)).check(matches(not(isDisplayed())));
        onView(withId(R.id.suggest_recycler)).check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
    }

    @Test
    public void citySelection_showNewWeather() {
        SystemClock.sleep(1000);
        onView(withId(R.id.suggest_recycler)).check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        SystemClock.sleep(400);

        onView(withId(R.id.weather_fragment_layout)).check(matches(isCompletelyDisplayed()));
    }
}
