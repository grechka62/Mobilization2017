package com.exwhythat.mobilization;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.exwhythat.mobilization.di.AppComponent;
import com.exwhythat.mobilization.di.DaggerAppComponent;
import com.exwhythat.mobilization.di.module.AppModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by exwhythat on 09.07.17.
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        // Init dagger app modules
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        // Init Timber and Stetho in debug mode only
        if (BuildConfig.DEBUG) {
            Timber.plant(new AppDebugTree());
            Stetho.initializeWithDefaults(this);
        }
    }

    @NonNull
    public static App get(Context context) {
        return ((App)context.getApplicationContext());
    }

    /** Customized Timber.DebugTree */
    private class AppDebugTree extends Timber.DebugTree {
        @Override
        protected String createStackElementTag(StackTraceElement element) {
            // Add line number to every log message
            return super.createStackElementTag(element) + ":" + element.getLineNumber();
        }
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}
