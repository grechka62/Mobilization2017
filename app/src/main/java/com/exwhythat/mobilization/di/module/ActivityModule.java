package com.exwhythat.mobilization.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
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
    private AppCompatActivity activity;

    public ActivityModule(@NonNull AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    @NonNull
    Context provideContext() {
        return activity;
    }

    @Provides
    @NonNull
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    @NonNull
    MainPresenter<MainView> provideMainPresenter(
            @NonNull MainPresenterImpl<MainView> presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    WeatherPresenter<WeatherView> provideWeatherPresenter(
            @NonNull WeatherPresenterImpl<WeatherView> presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    SettingsPresenter<SettingsView> provideSettingsPresenter(
            @NonNull SettingsPresenterImpl<SettingsView> presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    AboutPresenter<AboutView> provideAboutPresenter(
            @NonNull AboutPresenterImpl<AboutView> presenter) {
        return presenter;
    }
}
