package com.exwhythat.mobilization.model;

import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.part.Weather;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherItem {

    private String main;
    private String description;

    private Double temp;

    private Long date;

    private String city;

    public WeatherItem(WeatherResponse response) {
        // TODO: Check when it could be more than one
        Weather weather = response.getWeatherList().get(0);
        this.main = weather.getMain();
        this.description = weather.getDescription();

        this.temp = response.getMain().getTemp();

        this.date = response.getDate();
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public Double getTemp() {
        return temp;
    }

    public Long getDate() {
        return date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
