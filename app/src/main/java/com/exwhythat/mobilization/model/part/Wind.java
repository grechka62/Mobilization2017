package com.exwhythat.mobilization.model.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 15.07.17.
 */

public class Wind {

    @SerializedName("speed")
    @Expose
    private double speed;

    @SerializedName("deg")
    @Expose
    private double degree;

    public Wind(double speed, double degree) {
        this.speed = speed;
        this.degree = degree;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDegree() {
        return degree;
    }

}
