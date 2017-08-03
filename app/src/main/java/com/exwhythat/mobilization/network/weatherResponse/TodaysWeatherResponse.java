package com.exwhythat.mobilization.network.weatherResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Grechka on 03.08.2017.
 */

public class TodaysWeatherResponse {

    @SerializedName("cnt")
    @Expose
    private int count;

    @SerializedName("list")
    @Expose
    private List<WeatherResponse> todaysWeatherList = null;

    public int getCount() {
        return count;
    }

    public List<WeatherResponse> getTodaysWeatherList() {
        return todaysWeatherList;
    }
}
