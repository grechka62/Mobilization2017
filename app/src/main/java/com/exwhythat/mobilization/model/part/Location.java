
package com.exwhythat.mobilization.model.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lng")
    @Expose
    private double lng;

    public Location(double latitude, double longitude) {
        lat = latitude;
        lng = longitude;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("latutide=").append(lat);
        sb.append(", longitude=").append(lng);
        sb.append('}');
        return sb.toString();
    }
}
