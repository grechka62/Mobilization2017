package com.exwhythat.mobilization.ui.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.model.WeatherItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Grechka on 08.08.2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<WeatherItem> forecast = new ArrayList<>();
    //private View.OnClickListener listener;

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_forecast, parent, false);
        //v.setOnClickListener(listener);
        return new ForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ForecastViewHolder holder, int position) {
        holder.setWeather(forecast.get(position));
    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }

    /*public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }*/

    public void clear() {
        forecast.clear();
    }

    public void add(WeatherItem weatherItem) {
        forecast.add(weatherItem);
    }

    public void addAll(List<WeatherItem> forecast) {
        this.forecast.clear();
        this.forecast.addAll(forecast);
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView temp;

        ForecastViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
        }

        void setWeather(WeatherItem weatherItem) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            date.setText(sdf.format(weatherItem.getWeatherTime() * 1000));
            temp.setText(Double.toString(weatherItem.getTemperature()));
        }
    }
}
