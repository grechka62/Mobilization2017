package com.exwhythat.mobilization.repository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.RestApi;

import javax.inject.Inject;

import io.reactivex.Single;
import com.exwhythat.mobilization.util.Constants;

/**
 * Created by exwhythat on 16.07.17.
 */

public class RemoteWeatherRepository implements WeatherRepository {

    private RestApi restApi;

    @Inject
    public RemoteWeatherRepository(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather() {
        return restApi
                .getWeatherForCity(Constants.CityIds.MOSCOW, Constants.Units.METRIC)
                .map(WeatherItem::new);
    }
}