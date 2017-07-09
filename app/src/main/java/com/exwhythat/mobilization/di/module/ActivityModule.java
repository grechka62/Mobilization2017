package com.exwhythat.mobilization.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.exwhythat.mobilization.di.ActivityContext;
import com.exwhythat.mobilization.ui.main.MainPresenter;
import com.exwhythat.mobilization.ui.main.MainPresenterImpl;
import com.exwhythat.mobilization.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by exwhythat on 09.07.17.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    MainPresenter<MainView> provideMainPresenter(
            MainPresenterImpl<MainView> presenter) {
        return presenter;
    }
}
