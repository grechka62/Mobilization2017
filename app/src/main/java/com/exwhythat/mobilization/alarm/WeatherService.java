package com.exwhythat.mobilization.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
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

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import com.exwhythat.mobilization.util.Constants;
import com.exwhythat.mobilization.util.DataPrefs;
import com.exwhythat.mobilization.util.SettingPrefs;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherService extends Service {

    @Inject
    @ApplicationContext
    Context appContext;

    @Inject
    RestApi restApi;

    private Disposable disposable;

    private static Toast toast;

    @Override
    public void onCreate() {
        ServiceComponent component = DaggerServiceComponent.builder()
                .appModule(new AppModule(getApplication()))
                .networkModule(new NetworkModule())
                .build();
        component.inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        disposable = restApi.getWeatherForCity(Constants.CityIds.MOSCOW, Constants.Units.METRIC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        return START_NOT_STICKY;
    }

    private void onSuccess(WeatherResponse weatherResponse) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(weatherResponse);
        DataPrefs.putWeatherData(appContext, json);

        // Trigger prefs change listener even if unchanged, just to demonstrate result
        SettingPrefs.getSettingsPrefs(appContext)
                .edit()
                .putLong("temporaryPref", new Date().getTime())
                .apply();

        showToast("Tick success!");
    }

    private void onError(Throwable throwable) {
        Timber.d(throwable.getMessage());
        showToast("Tick error!");
    }

    private void showToast(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(appContext, message, Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
