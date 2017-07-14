package com.exwhythat.mobilization.ui.main;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.ui.about.AboutFragment;
import com.exwhythat.mobilization.ui.base.BaseActivity;
import com.exwhythat.mobilization.ui.base.BaseFragment;
import com.exwhythat.mobilization.ui.settings.SettingsFragment;
import com.exwhythat.mobilization.ui.weather.WeatherFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainPresenter<MainView> presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @IntDef({FragmentCodes.WEATHER, FragmentCodes.SETTINGS, FragmentCodes.ABOUT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface FragmentCodes {
        int WEATHER = 0;
        int SETTINGS = 1;
        int ABOUT = 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        setSupportActionBar(toolbar);
        initNavigationDrawer(toolbar);
        presenter.onAttach(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_placeholder, WeatherFragment.newInstance(), WeatherFragment.TAG)
                    .commit();
        }
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_weather:
                presenter.onDrawerWeatherClick();
                break;
            case R.id.nav_settings:
                presenter.onDrawerSettingsClick();
                break;
            case R.id.nav_about:
                presenter.onDrawerAboutClick();
                break;
            default:
                throw new IllegalStateException("Navigation drawer undeclared item");
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (isFragmentVisible(WeatherFragment.TAG)) {
            super.onBackPressed();
        } else {
            showWeather();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showWeather() {
        showFragment(FragmentCodes.WEATHER);
    }

    @Override
    public void showAbout() {
        showFragment(FragmentCodes.ABOUT);
    }

    @Override
    public void showSettings() {
        showFragment(FragmentCodes.SETTINGS);
    }

    /**
     * Awful navigation implementation, need to rework
     */
    private void showFragment(@FragmentCodes int fragmentCode) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        String tag;
        BaseFragment newFragment;
        int titleResId;

        switch (fragmentCode) {
            case FragmentCodes.WEATHER:
                tag = WeatherFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = WeatherFragment.newInstance();
                titleResId = R.string.action_weather;

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case FragmentCodes.SETTINGS:
                tag = SettingsFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = SettingsFragment.newInstance();
                titleResId = R.string.action_settings;
                break;
            case FragmentCodes.ABOUT:
                tag = AboutFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = AboutFragment.newInstance();
                titleResId = R.string.action_about;
                break;
            default:
                throw new IllegalArgumentException("Unsupported fragment code");
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (fragmentCode != FragmentCodes.WEATHER) {
            ft.addToBackStack(tag);
        }
        ft.setCustomAnimations(R.anim.slide_left, R.anim.slide_right, R.anim.slide_left, R.anim.slide_right);
        ft.replace(R.id.fragment_placeholder, newFragment, tag);
        ft.commit();

        setTitle(getString(titleResId));

        if (fragmentCode == FragmentCodes.WEATHER) {
            showToolbarHamburger();
            setDrawerEnabledState(true);
        } else {
            showToolbarArrow();
            setDrawerEnabledState(false);
        }
    }

    private void showToolbarHamburger() {
        // Remove back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Show hamburger
        drawerToggle.setDrawerIndicatorEnabled(true);
        // Remove the/any drawer toggle listener
        drawerToggle.setToolbarNavigationClickListener(null);
    }

    private void showToolbarArrow() {
        // Remove hamburger
        drawerToggle.setDrawerIndicatorEnabled(false);
        // Show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeather();
            }
        });
    }

    private void setDrawerEnabledState(boolean isEnabled) {
        drawerLayout.setDrawerLockMode(isEnabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private boolean isFragmentVisible(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int stackSize = fragmentManager.getBackStackEntryCount();
        if (stackSize > 0) {
            int lastIndex = stackSize - 1;
            return (fragmentManager.getBackStackEntryAt(lastIndex).getName().equals(tag));
        } else {
            if (tag.equals(WeatherFragment.TAG)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }
}
