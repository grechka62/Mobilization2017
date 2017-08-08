package com.exwhythat.mobilization.model;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by Grechka on 06.08.2017.
 */

public class CheckedCity {

    @Column("_id") private long id = 0;
    @Column("city_id") private long cityId;

    public CheckedCity(){};

    public CheckedCity(long id) {
        cityId = id;
    }

    public void setCityId(long id) {
        cityId = id;
    }

    public long getCityId() {
        return cityId;
    }
}
