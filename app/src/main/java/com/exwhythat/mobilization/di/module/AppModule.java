package com.exwhythat.mobilization.di.module;

import android.app.Application;
import android.content.Context;

import com.exwhythat.mobilization.di.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by exwhythat on 09.07.17.
 */

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application appContext) {
        mApplication = appContext;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }
}
