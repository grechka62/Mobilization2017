package com.exwhythat.mobilization.network.weatherResponse.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 15.07.17.
 */

public class Sys {
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private long sunrise;
    @SerializedName("sunset")
    @Expose
    private long sunset;
}
