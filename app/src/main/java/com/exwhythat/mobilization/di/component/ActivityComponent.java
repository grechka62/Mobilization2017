package com.exwhythat.mobilization.di.component;

import com.exwhythat.mobilization.di.module.ActivityModule;
import com.exwhythat.mobilization.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by exwhythat on 09.07.17.
 */

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}
