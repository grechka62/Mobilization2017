
package com.exwhythat.mobilization.network.suggestResponse.part;

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

    public String getDescription() {
        return description;
    }

    public String getPlaceId() {
        return placeId;
    }

}
