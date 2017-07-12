package com.exwhythat.mobilization.ui.weather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import javax.inject.Inject;

public class WeatherFragment extends BaseFragment implements WeatherView {

    public static final String TAG = "WeatherFragment";

    @Inject
    WeatherPresenter<WeatherView> presenter;

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
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
