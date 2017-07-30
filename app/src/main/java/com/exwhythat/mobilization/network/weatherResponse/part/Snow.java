package com.exwhythat.mobilization.network.weatherResponse.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 15.07.17.
 */

public class Snow {
    @SerializedName("3h")
    @Expose
    private double last3h;
}
