package com.exwhythat.mobilization.model;

import android.support.annotation.IntDef;

import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.ForecastResponse;
import com.exwhythat.mobilization.model.part.Main;
import com.exwhythat.mobilization.model.part.Wind;
import com.exwhythat.mobilization.ui.main.MainActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

import nl.qbusict.cupboard.annotation.Column;
import nl.qbusict.cupboard.annotation.Ignore;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherItem {
    @IntDef({WeatherTypes.CURRENT, WeatherTypes.TODAY, WeatherTypes.FORECAST})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface WeatherTypes {
        int CURRENT = 0;
        int TODAY = 1;
        int FORECAST = 2;
    }

    @Column("_id") private long id;
    @Column("city_id") private long cityId;
    @Column("update_time") private long updateTime;
    @Column("weather_time") private long weatherTime;
    private String description;
    private double temperature;
    private double humidity;
    @Column("wind_speed") private double windSpeed;
    @Column("wind_degree") private double windDegree;
    @WeatherTypes private int type;

    public WeatherItem(){};

    public WeatherItem(WeatherResponse response) {
        updateTime = Calendar.getInstance().getTimeInMillis();
        weatherTime = updateTime;
        description = response.getWeatherList().get(0).getDescription();
        Main main = response.getMain();
        temperature = main.getTemperature();
        humidity = main.getHumidity();
        windSpeed = response.getWind().getSpeed();
        windDegree = response.getWind().getDegree();
        type = WeatherTypes.CURRENT;
    }

    public WeatherItem(ForecastResponse response) {
        updateTime = Calendar.getInstance().getTimeInMillis();
        weatherTime = response.getWeatherTime();
        description = response.getWeather().get(0).getDescription();
        temperature = response.getTemperatures().getDayTemp();
        humidity = response.getHumidity();
        windSpeed = response.getWindSpeed();
        windDegree = response.getWindDegree();
        type = WeatherTypes.FORECAST;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setCity(long cityId) {
        this.cityId = cityId;
    }

    public long getCity() {
        return cityId;
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

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setType(@WeatherTypes int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
