package com.exwhythat.mobilization.model;

import android.support.annotation.IntDef;

import com.exwhythat.mobilization.model.part.Location;
import com.exwhythat.mobilization.network.cityResponse.CityResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by Grechka on 26.07.2017.
 */

public class City {
    @IntDef({City.IsDefault.YES, City.IsDefault.NO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IsDefault {
        int NO = 0;
        int YES = 1;
    }

    @Column("_id") private long id;
    @Column("place_id") private String placeId;
    private String name;
    private double latitude;
    private double longitude;

    public City() {}

    public City(City city) {
        id = city.getId();
        placeId = city.getPlaceId();
        name = city.getName();
        latitude = city.getLatitude();
        longitude = city.getLongitude();
    }

    public City(CityResponse cityResponse) {
        name = cityResponse.getResult().getName();
        latitude = cityResponse.getResult().getGeometry().getLocation().getLat();
        longitude = cityResponse.getResult().getGeometry().getLocation().getLng();
        placeId = cityResponse.getResult().getPlaceId();
    }

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
