package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.model.part.Location;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.RxDatabase;

/**
 * Created by exwhythat on 16.07.17.
 */

public class LocalWeatherRepository implements WeatherRepository {

    private RxDatabase database;

    @Inject
    public LocalWeatherRepository(RxDatabase database) {
        this.database = database;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather(Location location) {
        return null;
    }

    @Override
    public Observable<WeatherItem> getTodayWeather(Location location) {
        return null;
    }

    @Override
    public Observable<WeatherItem> getForecast(Location location) {
        return null;
    }
}