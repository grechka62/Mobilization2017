package com.exwhythat.mobilization.repository.weatherRepository;

import android.content.Context;

import com.exwhythat.mobilization.di.ActivityContext;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.network.cityResponse.part.Location;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.util.Constants;
import com.exwhythat.mobilization.util.DataPrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by exwhythat on 16.07.17.
 */

public class RemoteWeatherRepository implements WeatherRepository {

    private WeatherApi weatherApi;
    private Context context;

    @Inject
    public RemoteWeatherRepository(WeatherApi weatherApi, @ActivityContext Context context) {
        this.weatherApi = weatherApi;
        this.context = context;
    }

    @Override
    public Single<WeatherResponse> getCurrentWeather(Location location) {
        return weatherApi
                .getWeatherForCity(location.getLat(), location.getLng(),
                        Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE)
                .doOnSuccess(weatherResponse -> {
                    Gson gson = new GsonBuilder().create();
                    Type jsonType = new TypeToken<WeatherResponse>(){}.getType();
                    DataPrefs.putWeatherData(context, gson.toJson(weatherResponse, jsonType));
                });
    }
}