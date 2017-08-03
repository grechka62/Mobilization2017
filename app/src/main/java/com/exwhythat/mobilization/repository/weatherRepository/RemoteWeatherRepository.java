package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.model.part.Location;
import com.exwhythat.mobilization.network.weatherResponse.ForecastWeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.TodaysWeatherResponse;
import com.exwhythat.mobilization.util.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

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
    public Single<WeatherItem> getCurrentWeather(Location location) {
        return weatherApi
                .getCurrentWeatherForCity(location.getLat(), location.getLng(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(WeatherItem::new);
    }

    @Override
    public Observable<WeatherItem> getTodayWeather(Location location) {
        return weatherApi
                .getTodayWeather(location.getLat(), location.getLng(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(TodaysWeatherResponse::getTodaysWeatherList)
                .flatMapObservable(Observable::fromIterable)
                .map(WeatherItem::new);
    }

    @Override
    public Observable<WeatherItem> getForecast(Location location) {
        return weatherApi
                .getForecast(location.getLat(), location.getLng(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(ForecastWeatherResponse::getForecasts)
                .flatMapObservable(Observable::fromIterable)
                .map(WeatherItem::new);
    }
}