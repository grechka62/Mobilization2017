package com.exwhythat.mobilization.ui.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import javax.inject.Inject;


public class SettingsFragment extends BaseFragment implements SettingsView {

    public static final String TAG = "SettingsFragment";

    @Inject
    SettingsPresenterImpl<SettingsView> presenter;

    public SettingsFragment() {}

    @NonNull
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
