package com.exwhythat.mobilization.network.weatherResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Grechka on 03.08.2017.
 */

public class DailyTemperature {

    @SerializedName("day")
    @Expose
    public double dayTemp;

    @SerializedName("min")
    @Expose
    public double minTemp;

    @SerializedName("max")
    @Expose
    public double maxTemp;

    @SerializedName("night")
    @Expose
    public double nightTemp;

    @SerializedName("eve")
    @Expose
    public double eveningTemp;

    @SerializedName("morn")
    @Expose
    public double morningTemp;

    public double getDayTemp() {
        return dayTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getNightTemp() {
        return nightTemp;
    }

    public double getEveningTemp() {
        return eveningTemp;
    }

    public double getMorningTemp() {
        return morningTemp;
    }
}
