package com.exwhythat.mobilization.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by exwhythat on 09.07.17.
 */

@Module
public class AppModule {

    private final Application application;

    public AppModule(@NonNull Application appContext) {
        application = appContext;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @NonNull
    @Singleton
    Application provideApplication() {
        return application;
    }
}
