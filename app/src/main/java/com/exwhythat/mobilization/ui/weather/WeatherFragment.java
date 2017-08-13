package com.exwhythat.mobilization.ui.weather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class WeatherFragment extends BaseFragment implements WeatherView,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = WeatherFragment.class.getCanonicalName();
    private final static String CHECKED_CITY = "city";
    private long checkedCityId;

    @Inject
    WeatherPresenter presenter;

    SwipeRefreshLayout swipeRefreshLayout;

    private TextView temperature;
    private TextView description;
    private TextView humidity;
    private TextView wind;

    private ForecastAdapter forecastAdapter;

    public WeatherFragment() {
    }

    @NonNull
    public static WeatherFragment newInstance(long checkedCityId) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putLong(CHECKED_CITY, checkedCityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        if (getArguments() != null) {
            checkedCityId = getArguments().getLong(CHECKED_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        temperature = ButterKnife.findById(view, R.id.temperature);
        description = ButterKnife.findById(view, R.id.description);
        humidity = ButterKnife.findById(view, R.id.humidity_value);
        wind = ButterKnife.findById(view, R.id.wind_value);
        RecyclerView forecastList = ButterKnife.findById(view, R.id.forecast_recycler);

        forecastAdapter = new ForecastAdapter();
        forecastList.setLayoutManager(new LinearLayoutManager(getContext()));
        forecastList.setAdapter(forecastAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onAttach(this);
        presenter.observeCheckedCity();
        presenter.observeWeather();
        presenter.obtainWeather(checkedCityId);
    }

    @Override
    public void onRefresh() {
        presenter.refreshWeather();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
        forecastAdapter.clear();
    }

    @Override
    public void showResult(WeatherItem item) {
        checkedCityId = item.getCity();
        getArguments().putLong(CHECKED_CITY, checkedCityId);
        temperature.setText(Long.toString(Math.round(item.getTemperature())));
        description.setText(item.getDescription());
        humidity.setText(Long.toString(Math.round(item.getHumidity())));
        wind.setText(Long.toString(Math.round(item.getWindSpeed())));
        forecastAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showForecast(List<WeatherItem> forecast) {
        forecastAdapter.addAll(forecast);
    }

    public void showForecastItem(WeatherItem weatherItem) {
        forecastAdapter.add(weatherItem);
    }

    @Override
    public void showError(Throwable throwable) {
        String errorText = String.format(getString(R.string.error_with_msg), throwable.getLocalizedMessage());
        Toast.makeText(getContext(), errorText, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }
}
