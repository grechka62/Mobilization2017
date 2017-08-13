
package com.exwhythat.mobilization.network.suggestResponse;

import com.exwhythat.mobilization.network.suggestResponse.SuggestResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("place_id")
    @Expose
    private String placeId;

    public Prediction() {}

    public Prediction(String desc, String id) {
        description = desc;
        placeId = id;
    }

    public String getDescription() {
        return description;
    }

    public String getPlaceId() {
        return placeId;
    }

}
