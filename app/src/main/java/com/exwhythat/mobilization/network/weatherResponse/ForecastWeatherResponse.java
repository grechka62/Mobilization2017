package com.exwhythat.mobilization.network.weatherResponse;

import com.exwhythat.mobilization.model.City;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Grechka on 03.08.2017.
 */

public class ForecastWeatherResponse {

    @SerializedName("city")
    @Expose
    public City city;

    @SerializedName("cnt")
    @Expose
    public int count;

    @SerializedName("list")
    @Expose
    public List<ForecastResponse> forecasts = null;

    public List<ForecastResponse> getForecasts() {
        return forecasts;
    }
}
