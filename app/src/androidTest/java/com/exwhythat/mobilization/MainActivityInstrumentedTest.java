package com.exwhythat.mobilization;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatImageButton;

import com.exwhythat.mobilization.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Grechka on 28.07.2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void mainActivity_openDrawer() {
        onView(withClassName(equalTo(AppCompatImageButton.class.getName()))).perform(click());
    }

    @Test
    public void mainActivity_onCreate() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivity_goToCitySelection() {
        onView(withText(R.string.action_add_city)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.edit_city)).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivity_goToSettings() {
        onView(withText(R.string.action_settings)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.rb1h)).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivity_goToAbout() {
        onView(withText(R.string.action_about)).check(matches(isDisplayed())).perform(click());
        onView(withText(R.string.hello_about_fragment));
    }

    @Test
    public void mainActivity_goBackNotFromWeather() {
        onView(withText(R.string.action_about)).perform(click());
        pressBack();
        onView(withId(R.id.weather_fragment_layout)).check(matches(isCompletelyDisplayed()));
    }
}
