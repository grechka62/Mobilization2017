package com.exwhythat.mobilization;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.exwhythat.mobilization.di.module.NetworkModule;
import com.exwhythat.mobilization.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Grechka on 29.07.2017.
 */

public class WeatherFragmentInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void weatherFragment_onRefresh() {
        onView(withId(R.id.action_refresh)).check(matches(isDisplayed())).perform(click());
        SystemClock.sleep(1500);

        onView(withId(R.id.tvResult)).check(matches(isDisplayed()));
    }
}
