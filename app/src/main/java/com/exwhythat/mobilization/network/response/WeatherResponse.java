package com.exwhythat.mobilization.network.response;

import com.exwhythat.mobilization.network.response.part.Clouds;
import com.exwhythat.mobilization.network.response.part.Coord;
import com.exwhythat.mobilization.network.response.part.Main;
import com.exwhythat.mobilization.network.response.part.Rain;
import com.exwhythat.mobilization.network.response.part.Sys;
import com.exwhythat.mobilization.network.response.part.Weather;
import com.exwhythat.mobilization.network.response.part.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherResponse {
    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("rain")
    @Expose
    private Rain rain;
    @SerializedName("dt")
    @Expose
    private Long date;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Integer cityId;
    @SerializedName("name")
    @Expose
    private String cityName;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeatherList() {
        return weather;
    }

    public Long getDate() {
        return date;
    }
}
