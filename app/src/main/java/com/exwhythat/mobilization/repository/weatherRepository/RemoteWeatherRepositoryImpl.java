package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.network.weatherResponse.ForecastWeatherResponse;
import com.exwhythat.mobilization.util.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by exwhythat on 16.07.17.
 */

public class RemoteWeatherRepositoryImpl implements WeatherRepository {

    private WeatherApi weatherApi;

    @Inject
    public RemoteWeatherRepositoryImpl(WeatherApi weatherApi) {
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
    public Observable<WeatherItem> getForecast(City city) {
        return weatherApi
                .getForecast(city.getLatitude(), city.getLongitude(), 10,
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .map(ForecastWeatherResponse::getForecasts)
                .flatMapObservable(Observable::fromIterable)
                .map(WeatherItem::new);
    }
}