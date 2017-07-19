package com.exwhythat.mobilization.ui.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.alarm.WeatherAlarm;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import util.SettingPrefs;


public class SettingsFragment extends BaseFragment implements SettingsView {

    public static final String TAG = SettingsFragment.class.getCanonicalName();

    @Inject
    SettingsPresenterImpl<SettingsView> presenter;

    public SettingsFragment() {}

    RadioGroup rgUpdateInterval;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // FIXME: ugly radiogroup with hardcoded intervals... yak
        rgUpdateInterval = ButterKnife.findById(view, R.id.rgUpdateInterval);
        rgUpdateInterval.setOnCheckedChangeListener((radioGroup, idChecked) -> {
            switch (idChecked) {
                case R.id.rb1s:
                    SettingPrefs.putSettingsUpdateInterval(getContext(), 1);
                    WeatherAlarm.setAlarm(getContext(), 1);
                    break;
                case R.id.rb10s:
                    SettingPrefs.putSettingsUpdateInterval(getContext(), 10);
                    WeatherAlarm.setAlarm(getContext(), 10);
                    break;
                case R.id.rb1m:
                    SettingPrefs.putSettingsUpdateInterval(getContext(), 60);
                    WeatherAlarm.setAlarm(getContext(), 60);
                    break;
                case R.id.rb10m:
                    SettingPrefs.putSettingsUpdateInterval(getContext(), 60 * 10);
                    WeatherAlarm.setAlarm(getContext(), 60 * 10);
                    break;
                case R.id.rb30m:
                    SettingPrefs.putSettingsUpdateInterval(getContext(), 60 * 30);
                    WeatherAlarm.setAlarm(getContext(), 60 * 30);
                    break;
                case R.id.rb1h:
                    SettingPrefs.putSettingsUpdateInterval(getContext(), 60 * 60);
                    WeatherAlarm.setAlarm(getContext(), 60 * 60);
                    break;
                default:
                    break;
            }
        });

        int interval = SettingPrefs.getSettingsUpdateInterval(getContext());
        switch (interval) {
            case 1:
                rgUpdateInterval.check(R.id.rb1s);
                break;
            case 10:
                rgUpdateInterval.check(R.id.rb10s);
                break;
            case 60:
                rgUpdateInterval.check(R.id.rb1m);
                break;
            case 60*10:
                rgUpdateInterval.check(R.id.rb10m);
                break;
            case 60*30:
                rgUpdateInterval.check(R.id.rb30m);
                break;
            case 60*60:
                rgUpdateInterval.check(R.id.rb1h);
                break;
            default:
                rgUpdateInterval.check(R.id.rb10s);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.action_settings);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
