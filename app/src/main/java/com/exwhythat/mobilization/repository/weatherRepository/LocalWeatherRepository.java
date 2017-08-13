package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.WeatherItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by Grechka on 11.08.2017.
 */

public interface LocalWeatherRepository extends WeatherRepository {

    Single<WeatherItem> putCurrentWeather(WeatherItem weatherItem);

    Observable<WeatherItem> putForecast(List<WeatherItem> forecast);

    Flowable<DatabaseChange<WeatherItem>> observeWeather();
}
