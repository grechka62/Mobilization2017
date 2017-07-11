package com.exwhythat.mobilization.ui.main;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.ui.about.AboutFragment;
import com.exwhythat.mobilization.ui.base.BaseActivity;
import com.exwhythat.mobilization.ui.base.BaseFragment;
import com.exwhythat.mobilization.ui.settings.SettingsFragment;
import com.exwhythat.mobilization.ui.weather.WeatherFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainPresenter<MainView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        setSupportActionBar(mToolbar);
        initNavigationDrawer(mToolbar);
        mPresenter.onAttach(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, WeatherFragment.newInstance())
                    .commit();
        }
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_weather:
                mPresenter.onDrawerWeatherClick();
                break;
            case R.id.nav_settings:
                mPresenter.onDrawerSettingsClick();
                break;
            case R.id.nav_about:
                mPresenter.onDrawerAboutClick();
                break;
            default:
                throw new IllegalStateException("Navigation drawer undeclared item");
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showWeatherFragment() {
        navigateToFragment(WeatherFragment.newInstance(), WeatherFragment.TAG, R.string.action_weather);
    }

    @Override
    public void showAboutFragment() {
        navigateToFragment(AboutFragment.newInstance(), AboutFragment.TAG, R.string.action_about);
    }

    @Override
    public void showSettingsFragment() {
        navigateToFragment(SettingsFragment.newInstance(), SettingsFragment.TAG, R.string.action_settings);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(mTitle);
        }
    }

    private void navigateToFragment(BaseFragment fragment, String tag, @StringRes int titleResId) {

        FragmentManager fm = getSupportFragmentManager();
        Fragment foundFragment = fm.findFragmentByTag(tag);
        if (foundFragment != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.fragment_placeholder, fragment, tag)
                .commit();
        setTitle(getString(titleResId));
    }
}
