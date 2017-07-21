package com.exwhythat.mobilization.repository;

import android.content.Context;

import com.exwhythat.mobilization.di.ActivityContext;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import io.reactivex.Single;
import com.exwhythat.mobilization.util.DataPrefs;

/**
 * Created by exwhythat on 16.07.17.
 */

public class LocalWeatherRepository implements WeatherRepository{

    private Context context;

    @Inject
    public LocalWeatherRepository(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather() {
        String jsonWeatherData = DataPrefs.getWeatherDataAsJsonString(context);

        Gson gson = new GsonBuilder().create();
        Type jsonType = new TypeToken<WeatherResponse>(){}.getType();
        WeatherResponse response = gson.fromJson(jsonWeatherData, jsonType);

        return Single.just(response).map(WeatherItem::new);
    }
}