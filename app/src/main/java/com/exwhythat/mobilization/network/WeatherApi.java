package com.exwhythat.mobilization.network;

import android.support.annotation.Nullable;

import com.exwhythat.mobilization.network.response.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by exwhythat on 15.07.17.
 */

public interface RestApi {

    @GET("weather")
    Single<WeatherResponse> getWeatherForCity(@Query("id") int cityId,
                                              @Nullable @Query("units") String tempUnits);
}
