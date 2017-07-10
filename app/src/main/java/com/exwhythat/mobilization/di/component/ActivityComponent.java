package com.exwhythat.mobilization.di.component;

import com.exwhythat.mobilization.di.module.ActivityModule;
import com.exwhythat.mobilization.ui.about.AboutActivity;
import com.exwhythat.mobilization.ui.main.MainActivity;
import com.exwhythat.mobilization.ui.settings.SettingsActivity;

import dagger.Component;

/**
 * Created by exwhythat on 09.07.17.
 */

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(SettingsActivity activity);
    void inject(AboutActivity activity);
}
