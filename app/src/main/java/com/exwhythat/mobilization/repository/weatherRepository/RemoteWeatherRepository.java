package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.network.weatherResponse.ForecastWeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.TodaysWeatherResponse;
import com.exwhythat.mobilization.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by exwhythat on 16.07.17.
 */

public class RemoteWeatherRepository implements WeatherRepository {

    private WeatherApi weatherApi;

    @Inject
    public RemoteWeatherRepository(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather(City city) {
        return weatherApi
                .getCurrentWeatherForCity(city.getLatitude(), city.getLongitude(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(WeatherItem::new);
    }

    @Override
    public Single<WeatherItem> putCurrentWeather(WeatherItem weatherItem) {
        return Single.just(weatherItem);
    }

    @Override
    public Observable<WeatherItem> getTodayWeather(City city) {
        return weatherApi
                .getTodayWeather(city.getLatitude(), city.getLongitude(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(TodaysWeatherResponse::getTodaysWeatherList)
                .flatMapObservable(Observable::fromIterable)
                .map(WeatherItem::new);
    }

    @Override
    public Observable<WeatherItem> getForecast(City city) {
        return weatherApi
                .getForecast(city.getLatitude(), city.getLongitude(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(ForecastWeatherResponse::getForecasts)
                .flatMapObservable(Observable::fromIterable)
                .map(WeatherItem::new);
    }

    @Override
    public Observable<WeatherItem> putWeatherList(List<WeatherItem> weatherList) {
        return Observable.fromIterable(weatherList);
    }

    @Override
    public Flowable<DatabaseChange<WeatherItem>> observeWeather() {
        return Flowable.just(new DatabaseChange.DatabaseUpdate());
    }
}