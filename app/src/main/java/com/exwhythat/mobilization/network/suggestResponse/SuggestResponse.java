
package com.exwhythat.mobilization.network.suggestResponse;

import java.util.List;

import com.exwhythat.mobilization.network.suggestResponse.part.Prediction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestResponse {

    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = null;

    public SuggestResponse() {}

    public SuggestResponse(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

}
