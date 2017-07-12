package com.exwhythat.mobilization.di.component;

import android.support.annotation.NonNull;

import com.exwhythat.mobilization.di.module.ActivityModule;
import com.exwhythat.mobilization.ui.about.AboutFragment;
import com.exwhythat.mobilization.ui.main.MainActivity;
import com.exwhythat.mobilization.ui.weather.WeatherFragment;
import com.exwhythat.mobilization.ui.settings.SettingsFragment;

import dagger.Component;

/**
 * Created by exwhythat on 09.07.17.
 */

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(@NonNull MainActivity activity);
    void inject(@NonNull WeatherFragment fragment);
    void inject(@NonNull AboutFragment fragment);
    void inject(@NonNull SettingsFragment fragment);
}
