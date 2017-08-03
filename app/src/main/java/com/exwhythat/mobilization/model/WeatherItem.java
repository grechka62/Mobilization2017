package com.exwhythat.mobilization.model;

import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.ForecastResponse;
import com.exwhythat.mobilization.model.part.Main;
import com.exwhythat.mobilization.model.part.Wind;

import java.util.Calendar;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherItem {

    private City city;
    private long updateTime;
    private long weatherTime;
    private String description;
    private double temperature;
    private double humidity;
    private Wind wind;

    public WeatherItem(WeatherResponse response) {
        updateTime = Calendar.getInstance().getTimeInMillis();
        weatherTime = updateTime;
        description = response.getWeatherList().get(0).getDescription();
        Main main = response.getMain();
        temperature = main.getTemperature();
        humidity = main.getHumidity();
        wind = response.getWind();
    }

    public WeatherItem(ForecastResponse response) {
        updateTime = Calendar.getInstance().getTimeInMillis();
        weatherTime = response.getWeatherTime();
        description = response.getWeather().get(0).getDescription();
        temperature = response.getTemperatures().getDayTemp();
        humidity = response.getHumidity();
        wind = new Wind(response.getWindSpeed(), response.getWindDegree());
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public long getWeatherTime() {
        return weatherTime;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public Wind getWind() {
        return wind;
    }
}
