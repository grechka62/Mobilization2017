package com.exwhythat.mobilization.model.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 15.07.17.
 */

public class Main {

    @SerializedName("temp")
    @Expose
    private double temperature;

    @SerializedName("pressure")
    @Expose
    private double pressure;

    @SerializedName("humidity")
    @Expose
    private double humidity;

    public Main(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    //TODO toString(), hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Main main = (Main) o;

        return temperature == main.getTemperature();
    }
}
