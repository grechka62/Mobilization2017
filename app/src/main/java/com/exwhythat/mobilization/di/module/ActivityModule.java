package com.exwhythat.mobilization.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.exwhythat.mobilization.di.ActivityContext;
import com.exwhythat.mobilization.ui.about.AboutPresenter;
import com.exwhythat.mobilization.ui.about.AboutPresenterImpl;
import com.exwhythat.mobilization.ui.about.AboutView;
import com.exwhythat.mobilization.ui.main.MainPresenter;
import com.exwhythat.mobilization.ui.main.MainPresenterImpl;
import com.exwhythat.mobilization.ui.main.MainView;
import com.exwhythat.mobilization.ui.settings.SettingsPresenter;
import com.exwhythat.mobilization.ui.settings.SettingsPresenterImpl;
import com.exwhythat.mobilization.ui.settings.SettingsView;
import com.exwhythat.mobilization.ui.weather.WeatherPresenter;
import com.exwhythat.mobilization.ui.weather.WeatherPresenterImpl;
import com.exwhythat.mobilization.ui.weather.WeatherView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by exwhythat on 09.07.17.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    MainPresenter<MainView> provideMainPresenter(
            MainPresenterImpl<MainView> presenter) {
        return presenter;
    }

    @Provides
    WeatherPresenter<WeatherView> provideWeatherPresenter(
            WeatherPresenterImpl<WeatherView> presenter) {
        return presenter;
    }

    @Provides
    SettingsPresenter<SettingsView> provideSettingsPresenter(
            SettingsPresenterImpl<SettingsView> presenter) {
        return presenter;
    }

    @Provides
    AboutPresenter<AboutView> provideAboutPresenter(
            AboutPresenterImpl<AboutView> presenter) {
        return presenter;
    }
}
