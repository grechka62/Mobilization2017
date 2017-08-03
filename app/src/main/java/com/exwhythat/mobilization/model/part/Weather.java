package com.exwhythat.mobilization.model.part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 15.07.17.
 */

public class Weather {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public Weather(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (main != null ? !main.equals(weather.getMain()) : weather.getMain() != null)
            return false;
        return (main != null ? main.equals(weather.getDescription()) : weather.getDescription() == null);
    }*/
}
