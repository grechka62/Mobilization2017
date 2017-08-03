package com.exwhythat.mobilization.di.module;

import android.support.annotation.NonNull;

import com.exwhythat.mobilization.ui.about.AboutPresenter;
import com.exwhythat.mobilization.ui.about.AboutPresenterImpl;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionPresenter;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionPresenterImpl;
import com.exwhythat.mobilization.ui.main.MainPresenter;
import com.exwhythat.mobilization.ui.main.MainPresenterImpl;
import com.exwhythat.mobilization.ui.settings.SettingsPresenter;
import com.exwhythat.mobilization.ui.settings.SettingsPresenterImpl;
import com.exwhythat.mobilization.ui.weather.WeatherPresenter;
import com.exwhythat.mobilization.ui.weather.WeatherPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by exwhythat on 14.07.17.
 */

@Module
public class PresenterModule {

    @Provides
    @NonNull
    MainPresenter provideMainPresenter(
            @NonNull MainPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    WeatherPresenter provideWeatherPresenter(
            @NonNull WeatherPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    SettingsPresenter provideSettingsPresenter(
            @NonNull SettingsPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    AboutPresenter provideAboutPresenter(
            @NonNull AboutPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @NonNull
    CitySelectionPresenter provideCitySelectionPresenter(
            @NonNull CitySelectionPresenterImpl presenter) {
        return presenter;
    }
}
