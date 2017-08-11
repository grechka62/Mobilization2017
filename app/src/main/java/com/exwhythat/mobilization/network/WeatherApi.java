package com.exwhythat.mobilization.network;

import android.support.annotation.Nullable;

import com.exwhythat.mobilization.network.weatherResponse.ForecastWeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by exwhythat on 15.07.17.
 */

public interface WeatherApi {
    String WEATHER_API_KEY_VALUE = "4ac1ae796a583e819faf2f2bec3f0aed";

    @GET("weather")
    Single<WeatherResponse> getCurrentWeatherForCity(@Query("lat") double lan,
                                                     @Query("lon") double lon,
                                                     @Nullable @Query("units") String tempUnits,
                                                     @Query("APPID") String key);

    @GET("forecast/daily")
    Single<ForecastWeatherResponse> getForecast(@Query("lat") double lan,
                                                @Query("lon") double lon,
                                                @Query("cnt") int count,
                                                @Nullable @Query("units") String tempUnits,
                                                @Query("APPID") String key);
}
