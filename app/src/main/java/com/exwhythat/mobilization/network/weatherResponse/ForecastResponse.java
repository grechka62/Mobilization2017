package com.exwhythat.mobilization.network.weatherResponse;

import com.exwhythat.mobilization.model.part.Weather;
import com.exwhythat.mobilization.network.weatherResponse.DailyTemperature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Grechka on 03.08.2017.
 */

public class ForecastResponse {

    @SerializedName("dt")
    @Expose
    private int weatherTime;

    @SerializedName("temp")
    @Expose
    private DailyTemperature temperatures;

    @SerializedName("pressure")
    @Expose
    private double pressure;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    @SerializedName("speed")
    @Expose
    private double windSpeed;

    @SerializedName("deg")
    @Expose
    private double windDegree;

    public int getWeatherTime() {
        return weatherTime;
    }

    public DailyTemperature getTemperatures() {
        return temperatures;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }
}
