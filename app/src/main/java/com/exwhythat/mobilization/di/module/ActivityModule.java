package com.exwhythat.mobilization.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.exwhythat.mobilization.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by exwhythat on 09.07.17.
 */

@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(@NonNull AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    @NonNull
    Context provideContext() {
        return activity;
    }

    @Provides
    @NonNull
    AppCompatActivity provideActivity() {
        return activity;
    }
}
