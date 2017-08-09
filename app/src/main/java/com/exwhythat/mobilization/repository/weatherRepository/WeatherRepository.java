package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by exwhythat on 16.07.17.
 */

public interface WeatherRepository {

    Single<WeatherItem> getCurrentWeather(City city);

    Single<WeatherItem> putCurrentWeather(WeatherItem weatherItem);

    Observable<WeatherItem> getTodayWeather(City city);

    Observable<WeatherItem> getForecast(City city);

    Observable<WeatherItem> putWeatherList(List<WeatherItem> weatherList);

    Flowable<DatabaseChange<WeatherItem>> observeWeather();
}
