package com.exwhythat.mobilization.di.component;

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
    void inject(MainActivity activity);
    void inject(WeatherFragment fragment);
    void inject(AboutFragment fragment);
    void inject(SettingsFragment fragment);
}
