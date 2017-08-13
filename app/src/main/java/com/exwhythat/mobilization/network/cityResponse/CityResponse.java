
package com.exwhythat.mobilization.network.cityResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse {

    @SerializedName("result")
    @Expose
    private Result result;

    public CityResponse(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
