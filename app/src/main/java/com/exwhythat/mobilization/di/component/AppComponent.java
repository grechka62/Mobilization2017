package com.exwhythat.mobilization.di.component;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by exwhythat on 09.07.17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject (App app);
}
