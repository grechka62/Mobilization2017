package com.exwhythat.mobilization.model;

import com.exwhythat.mobilization.network.cityResponse.CityResponse;
import com.exwhythat.mobilization.model.part.Location;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by Grechka on 26.07.2017.
 */

public class City {

    private long _id;
    private String name;
    private double latitude;
    private double longitude;

    public City(CityResponse cityResponse) {
        name = cityResponse.getResult().getName();
        latitude = cityResponse.getResult().getGeometry().getLocation().getLat();
        longitude = cityResponse.getResult().getGeometry().getLocation().getLng();
    }

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location getLocation() {
        return new Location(latitude, longitude);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("name='").append(name).append('\'');
        sb.append(", latutide=").append(latitude);
        sb.append(", longitude=").append(longitude);
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
