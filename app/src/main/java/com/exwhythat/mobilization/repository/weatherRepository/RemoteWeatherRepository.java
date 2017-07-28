package com.exwhythat.mobilization.repository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.util.Constants;

import javax.inject.Inject;

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
    public Single<WeatherItem> getCurrentWeather() {
        return weatherApi
                .getWeatherForCity(Constants.CityIds.MOSCOW, Constants.Units.METRIC)
                .map(WeatherItem::new);
    }
}