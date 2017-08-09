package com.exwhythat.mobilization.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.part.Location;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
import com.exwhythat.mobilization.util.CityPrefs;
import com.exwhythat.mobilization.util.Constants;
import com.exwhythat.mobilization.util.DataPrefs;
import com.exwhythat.mobilization.util.SettingPrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherService extends Service {

    @Inject
    Context appContext;

    @Inject
    WeatherRepository remoteRepo;
    @Inject
    LocalWeatherRepository localRepo;
    @Inject
    LocalCityRepository cityRepo;

    private Disposable disposable;

    @Override
    public void onCreate() {
        App.getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final City[] mCity = new City[1];
        disposable = cityRepo.getCheckedId()
                .flatMap(checkedCity -> cityRepo.getCheckedCity(checkedCity.getCityId()))
                .map(city -> mCity[0] = city)
                .flatMap(city -> remoteRepo.getCurrentWeather(city))
                .map(weatherItem -> {
                    weatherItem.setCity(mCity[0].getId());
                    return weatherItem;})
                .flatMap(weatherItem -> localRepo.putCurrentWeather(weatherItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {}, this::onError);
        return START_NOT_STICKY;
    }

    /*private void onSuccess(WeatherResponse weatherResponse) {
        //TODO сохранение в базу
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(weatherResponse);
        DataPrefs.putWeatherData(appContext, json);

        // Trigger prefs change listener even if unchanged, just to demonstrate result
        SettingPrefs.getSettingsPrefs(appContext)
                .edit()
                .putLong("temporaryPref", new Date().getTime())
                .apply();
    }*/

    private void onError(Throwable throwable) {
        Timber.d(throwable.getMessage());
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
