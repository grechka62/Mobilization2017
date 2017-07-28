package com.exwhythat.mobilization.ui.main;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.alarm.WeatherAlarm;
import com.exwhythat.mobilization.ui.about.AboutFragment;
import com.exwhythat.mobilization.ui.base.BaseActivity;
import com.exwhythat.mobilization.ui.base.BaseFragment;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionFragment;
import com.exwhythat.mobilization.ui.settings.SettingsFragment;
import com.exwhythat.mobilization.ui.weather.WeatherFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity
        implements MainView, NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    @Inject
    MainPresenter<MainView> presenter;

    private Disposable disposable = new CompositeDisposable();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerArrowDrawable homeDrawable;

    private boolean isHomeAsUp = false;

    @IntDef({FragmentCodes.WEATHER, FragmentCodes.SETTINGS, FragmentCodes.ABOUT, FragmentCodes.CITY_SELECTION})
    @Retention(RetentionPolicy.SOURCE)
    private @interface FragmentCodes {
        int WEATHER = 0;
        int SETTINGS = 1;
        int ABOUT = 2;
        int CITY_SELECTION = 3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));

        initToolbar();

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(this);

        presenter.onAttach(this);

        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_placeholder, WeatherFragment.newInstance(), WeatherFragment.TAG)
                    .commit();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setHomeAsUp(true);
        }

        WeatherAlarm.setAlarm(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        initNavigationDrawer(toolbar);
        homeDrawable = new DrawerArrowDrawable(toolbar.getContext());
        toolbar.setNavigationIcon(homeDrawable);
        toolbar.setNavigationOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START) || isHomeAsUp) {
                onBackPressed();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_weather);
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
            case R.id.action_refresh:
                // Delegate to WeatherFragment
                return false;
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
            case R.id.nav_city_selection:
                presenter.onDrawerCitySelectionClick();
                break;
            default:
                throw new IllegalStateException("Navigation drawer undeclared item");
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (isRootFragmentVisible()) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        disposable.dispose();
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

    @Override
    public void showCitySelection() {
        showFragment(FragmentCodes.CITY_SELECTION);
    }

    /**
     * Awful navigation implementation, need to rework
     */
    private void showFragment(@FragmentCodes int fragmentCode) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        String tag;
        BaseFragment newFragment;

        switch (fragmentCode) {
            case FragmentCodes.WEATHER:
                tag = WeatherFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = WeatherFragment.newInstance();

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case FragmentCodes.SETTINGS:
                tag = SettingsFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = SettingsFragment.newInstance();
                break;
            case FragmentCodes.ABOUT:
                tag = AboutFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = AboutFragment.newInstance();
                break;
            case FragmentCodes.CITY_SELECTION:
                tag = CitySelectionFragment.TAG;
                if (isFragmentVisible(tag)) {
                    return;
                }
                newFragment = CitySelectionFragment.newInstance();
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
    }

    private boolean isRootFragmentVisible() {
        return isFragmentVisible(WeatherFragment.TAG);
    }

    private boolean isFragmentVisible(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int stackSize = fragmentManager.getBackStackEntryCount();
        if (stackSize > 0) {
            int lastIndex = stackSize - 1;
            return (fragmentManager.getBackStackEntryAt(lastIndex).getName().equals(tag));
        } else {
            return tag.equals(WeatherFragment.TAG);
        }
    }

    private void setHomeAsUp(boolean isHomeAsUp) {
        if (this.isHomeAsUp != isHomeAsUp) {
            this.isHomeAsUp = isHomeAsUp;

            int lockMode = isHomeAsUp ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED;
            drawerLayout.setDrawerLockMode(lockMode);

            ValueAnimator anim = isHomeAsUp ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
            anim.addUpdateListener(valueAnimator -> {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                homeDrawable.setProgress(slideOffset);
            });
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(400);
            anim.start();
        }
    }

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setHomeAsUp(true);
        } else {
            setHomeAsUp(false);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(navigationView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
