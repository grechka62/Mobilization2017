package com.exwhythat.mobilization.model;

import android.support.annotation.NonNull;

import com.exwhythat.mobilization.model.part.Location;
import com.exwhythat.mobilization.network.cityResponse.CityResponse;
import com.exwhythat.mobilization.network.cityResponse.Result;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by Grechka on 26.07.2017.
 */

public class City {

    @Column("_id") private long id;
    @Column("place_id") private String placeId;
    private String name;
    private double latitude;
    private double longitude;

    public City() {
        placeId = "";
        name = "";
    }

    public City(@NonNull City city) {
        id = city.getId();
        placeId = city.getPlaceId();
        name = city.getName();
        latitude = city.getLatitude();
        longitude = city.getLongitude();
    }

    public City(@NonNull CityResponse cityResponse) {
        Result result = cityResponse.getResult();
        if (result.getName() != null) {
            name = result.getName();
        } else name = "";
        latitude = result.getGeometry().getLocation().getLat();
        longitude = result.getGeometry().getLocation().getLng();
        if (result.getPlaceId() != null) {
            placeId = result.getPlaceId();
        } else placeId = "";
    }

    public City(@NonNull String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        placeId = "";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPlaceId(String id) {
        placeId = id;
    }

    public String getPlaceId() {
        return placeId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (Double.compare(city.latitude, latitude) != 0) return false;
        if (Double.compare(city.longitude, longitude) != 0) return false;
        if (!placeId.equals(city.placeId)) return false;
        return name.equals(city.name);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = placeId.hashCode();
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
