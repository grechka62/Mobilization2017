package com.exwhythat.mobilization.ui.weather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class WeatherFragment extends BaseFragment implements WeatherView {

    public static final String TAG = WeatherFragment.class.getCanonicalName();
    private final static String CHECKED_CITY = "city";
    private long checkedCityId;

    @Inject
    WeatherPresenter presenter;

    private ProgressBar pbLoading;

    private TextView tvResult;
    private TextView tvError;

    public WeatherFragment() {}

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pbLoading = ButterKnife.findById(view, R.id.pbLoadingWeather);
        tvResult = ButterKnife.findById(view, R.id.tvResult);
        tvError = ButterKnife.findById(view, R.id.tvError);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.action_weather);
        presenter.onAttach(this);
        presenter.observeCheckedCity();
        presenter.observeWeather();
        presenter.onPrefsChanged(checkedCityId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.onRefreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        tvResult.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showResult(WeatherItem item) {
        checkedCityId = item.getCity();
        getArguments().putLong(CHECKED_CITY, checkedCityId);
        // TODO: extract data-related stuff to separate class
        Date date = new Date(item.getUpdateTime() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy\nHH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        // TODO: make a good layout for weather representation or use string holders
        tvResult.setText("City: " + item.getCity() + "\nDate: " + sdf.format(date) + "\nDesc: " + item.getDescription() + "\nTemp: " + item.getTemperature());

        tvResult.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        String errorText = String.format(getString(R.string.error_with_msg), throwable.getLocalizedMessage());
        tvError.setText(errorText);

        tvResult.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
    }
}
