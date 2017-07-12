package com.exwhythat.mobilization.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.exwhythat.mobilization.di.ApplicationContext;

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
    @ApplicationContext
    @NonNull
    Context provideContext() {
        return application;
    }

    @Provides
    @NonNull
    Application provideApplication() {
        return application;
    }
}
