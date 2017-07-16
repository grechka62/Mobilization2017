package com.exwhythat.mobilization.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.exwhythat.mobilization.di.ApplicationContext;
import com.exwhythat.mobilization.di.component.DaggerServiceComponent;
import com.exwhythat.mobilization.di.component.ServiceComponent;
import com.exwhythat.mobilization.di.module.AppModule;
import com.exwhythat.mobilization.di.module.NetworkModule;
import com.exwhythat.mobilization.network.RestApi;
import com.exwhythat.mobilization.network.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import util.Constants;
import util.Prefs;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String EXTRA_UPDATE_INTERVAL_SECONDS = "com.exwhythat.mobilization.service.EXTRA_UPDATE_INTERVAL_SECONDS";

    private static final int INTERVAL_SECONDS_DEFAULT = 30 * 60;

    private static final int INTERVAL_SECONDS_DEBUG = 10;

    @Inject
    @ApplicationContext
    Context appContext;

    @Inject
    RestApi restApi;

    @Inject
    SharedPreferences prefs;

    private Disposable disposable;

    @Override
    public void onCreate() {
        ServiceComponent component = DaggerServiceComponent.builder()
                .appModule(new AppModule(getApplication()))
                .networkModule(new NetworkModule())
                .build();
        component.inject(this);

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int interval = Prefs.getSettingsUpdateInterval(appContext);
        if (interval == -1) {
            interval = INTERVAL_SECONDS_DEBUG;
        }

        disposable = startEndlessWeatherUpdate(interval);

        return START_STICKY;
    }

    @NonNull
    private Disposable startEndlessWeatherUpdate(int interval) {
        // FIXME: for some reason interval stops after any error inside of it. It must be endless no matter what.
        return Flowable.interval(interval, TimeUnit.SECONDS)
                .doOnNext(this::handleUpdateTick)
                .onErrorResumeNext(throwable -> {
                    WeatherService.this.onError(throwable);
                    return Flowable.just(1L);
                })
                .retryWhen(throwableFlowable -> Flowable.just(1L))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void handleUpdateTick(Long intervalLong) {
        Single<WeatherResponse> weatherResponseSingle = restApi.getWeatherForCity(Constants.CityIds.MOSCOW,
                Constants.Units.METRIC);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(weatherResponseSingle.blockingGet());
        Prefs.putWeatherData(appContext, json);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(this, "Tick!", Toast.LENGTH_SHORT).show());
    }

    private void onError(Throwable throwable) {
        Timber.d(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        prefs.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Prefs.KEY_SETTING_UPDATE_INTERVAL.equals(key)) {
            // TODO: is there better way to update interval?
            stopSelf();
            startService(new Intent(this, WeatherService.class));
        }
    }
}
