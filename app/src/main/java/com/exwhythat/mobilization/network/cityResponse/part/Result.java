
package com.exwhythat.mobilization.network.cityResponse.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("name")
    @Expose
    private String name;

    public Result(String name, Geometry geometry) {
        this.name = name;
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

}
