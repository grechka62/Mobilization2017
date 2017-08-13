package com.exwhythat.mobilization.ui.weather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

    private TextView tvResult;
    private TextView tvError;

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
        tvResult = ButterKnife.findById(view, R.id.tvResult);
        tvError = ButterKnife.findById(view, R.id.tvError);
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
        getActivity().setTitle(R.string.action_weather);
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
        // TODO: extract data-related stuff to separate class
        Date date = new Date(item.getUpdateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy\nHH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        // TODO: make a good layout for weather representation or use string holders
        tvResult.setText("City: " + item.getCity() + "\nDate: " + sdf.format(date) + "\nDesc: " + item.getDescription() + "\nTemp: " + item.getTemperature());
        tvResult.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
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
        tvResult.setVisibility(View.GONE);
        tvError.setText(errorText);
        tvError.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }
}
