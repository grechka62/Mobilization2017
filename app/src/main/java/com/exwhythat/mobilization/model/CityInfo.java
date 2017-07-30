package com.exwhythat.mobilization.model;

import com.exwhythat.mobilization.network.cityResponse.CityResponse;
import com.exwhythat.mobilization.network.cityResponse.part.Location;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Grechka on 26.07.2017.
 */

public class CityInfo {

    private String name;
    private Location location;

    public CityInfo(CityResponse cityResponse) {
        name = cityResponse.getResult().getName();
        location = cityResponse.getResult().getGeometry().getLocation();
    }

    public CityInfo(String name, double latitude, double longitude) {
        this.name = name;
        this.location = new Location(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString());
    }
}
