package com.exwhythat.mobilization.network.weatherResponse.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 15.07.17.
 */

public class Main {
    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;
    @SerializedName("sea_level")
    @Expose
    private double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private double grndLevel;

    public double getTemp() {
        return temp;
    }
}
