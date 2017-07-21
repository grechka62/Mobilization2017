package com.exwhythat.mobilization.ui.weather;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.ButterKnife;
import util.Prefs;

public class WeatherFragment extends BaseFragment implements WeatherView,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = WeatherFragment.class.getCanonicalName();

    @Inject
    WeatherPresenter<WeatherView> presenter;

    private ProgressBar pbLoading;

    private Button btnRefresh;

    private TextView tvResult;
    private TextView tvError;

    public WeatherFragment() {}

    @NonNull
    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "View created!", Toast.LENGTH_SHORT).show();

        btnRefresh = ButterKnife.findById(view, R.id.btnRefreshData);
        if (btnRefresh != null) {
            btnRefresh.setOnClickListener(view2 -> presenter.onRefreshData());
        }
        pbLoading = ButterKnife.findById(view, R.id.pbLoadingWeather);
        tvResult = ButterKnife.findById(view, R.id.tvResult);
        tvError = ButterKnife.findById(view, R.id.tvError);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences weatherPrefs = Prefs.getDataPrefs(getContext());
        weatherPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences weatherPrefs = Prefs.getDataPrefs(getContext());
        weatherPrefs.unregisterOnSharedPreferenceChangeListener(this);
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
        btnRefresh.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showResult(WeatherItem item) {
        // TODO: extract data-related stuff to separate class
        long dateInSeconds = item.getDate();
        Date date = new Date(dateInSeconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy\nHH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        // TODO: make a good layout for weather representation or use string holders
        tvResult.setText("Date: " + sdf.format(date) + "\nMain: " + item.getMain() + "\nDesc: " + item.getDescription() + "\nTemp: " + item.getTemp());

        tvResult.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        String errorText = String.format(getString(R.string.error_with_msg), throwable.getLocalizedMessage());
        tvError.setText(errorText);

        tvResult.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        presenter.onPrefsChanged();
    }
}
