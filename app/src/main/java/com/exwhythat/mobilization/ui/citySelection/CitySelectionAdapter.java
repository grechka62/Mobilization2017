package com.exwhythat.mobilization.ui.citySelection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grechka on 26.07.2017.
 */

public class CitySelectionAdapter extends RecyclerView.Adapter<CitySelectionAdapter.CitySelectionViewHolder> {

    private List<Prediction> predictions = new ArrayList<>();
    private View.OnClickListener listener;

    @Override
    public CitySelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_city_suggestion, parent, false);
        v.setOnClickListener(listener);
        return new CitySelectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CitySelectionAdapter.CitySelectionViewHolder holder, int position) {
        holder.setSuggest(predictions.get(position));
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void clear() {
        predictions.clear();
    }

    public void add(Prediction prediction) {
        predictions.add(prediction);
    }

    class CitySelectionViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView placeId;

        CitySelectionViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.suggest_description);
            placeId = itemView.findViewById(R.id.place_id);
        }

        void setSuggest(Prediction prediction) {
            description.setText(prediction.getDescription());
            placeId.setText(prediction.getPlaceId());
        }
    }
}