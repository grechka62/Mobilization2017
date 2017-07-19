package com.exwhythat.mobilization.di.component;

import com.exwhythat.mobilization.di.module.AppModule;
import com.exwhythat.mobilization.di.module.NetworkModule;
import com.exwhythat.mobilization.alarm.WeatherService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by exwhythat on 16.07.17.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface ServiceComponent {
    void inject(WeatherService weatherService);
}