package com.exwhythat.mobilization.di;

import android.support.annotation.NonNull;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.alarm.WeatherService;
import com.exwhythat.mobilization.di.module.AppModule;
import com.exwhythat.mobilization.di.module.NetworkModule;
import com.exwhythat.mobilization.di.module.PresenterModule;
import com.exwhythat.mobilization.di.module.StorageModule;
import com.exwhythat.mobilization.ui.about.AboutFragment;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionFragment;
import com.exwhythat.mobilization.ui.main.MainActivity;
import com.exwhythat.mobilization.ui.settings.SettingsFragment;
import com.exwhythat.mobilization.ui.weather.WeatherFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by exwhythat on 09.07.17.
 */

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class, NetworkModule.class, StorageModule.class})
public interface AppComponent {
    void inject(@NonNull App app);
    void inject(@NonNull WeatherService weatherService);
    void inject(@NonNull MainActivity activity);
    void inject(@NonNull WeatherFragment fragment);
    void inject(@NonNull AboutFragment fragment);
    void inject(@NonNull SettingsFragment fragment);
    void inject(@NonNull CitySelectionFragment fragment);
}
