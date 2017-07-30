package com.exwhythat.mobilization.repository.weatherRepository;

import android.content.Context;

import com.exwhythat.mobilization.di.ActivityContext;
import com.exwhythat.mobilization.network.cityResponse.part.Location;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
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

public class LocalWeatherRepository implements WeatherRepository {

    private Context context;

    @Inject
    public LocalWeatherRepository(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public Single<WeatherResponse> getCurrentWeather(Location location) {
        String jsonWeatherData = DataPrefs.getWeatherDataAsJsonString(context);

        Gson gson = new GsonBuilder().create();
        Type jsonType = new TypeToken<WeatherResponse>(){}.getType();
        WeatherResponse response = gson.fromJson(jsonWeatherData, jsonType);

        return Single.just(response);
    }
}