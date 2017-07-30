package com.exwhythat.mobilization.network.weatherResponse;

import com.exwhythat.mobilization.network.weatherResponse.part.Clouds;
import com.exwhythat.mobilization.network.weatherResponse.part.Coord;
import com.exwhythat.mobilization.network.weatherResponse.part.Main;
import com.exwhythat.mobilization.network.weatherResponse.part.Rain;
import com.exwhythat.mobilization.network.weatherResponse.part.Sys;
import com.exwhythat.mobilization.network.weatherResponse.part.Weather;
import com.exwhythat.mobilization.network.weatherResponse.part.Wind;
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
    private long date;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private int cityId;
    @SerializedName("name")
    @Expose
    private String cityName;
    @SerializedName("cod")
    @Expose
    private int cod;

    public WeatherResponse(Main main, List<Weather> weather, long date) {
        this.main = main;
        this.weather = weather;
        this.date = date;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeatherList() {
        return weather;
    }

    public long getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherResponse weatherResponse = (WeatherResponse) o;

        if (main != null ? !main.equals(weatherResponse.getMain()) : weatherResponse.getMain() != null)
            return false;
        if (weather.get(0) != null ? !weather.get(0).equals(weatherResponse.getWeatherList().get(0)) :
                weatherResponse.getWeatherList().get(0) != null)
            return false;
        return date == weatherResponse.getDate();
    }
}
