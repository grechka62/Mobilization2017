package com.exwhythat.mobilization.ui.settings;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.alarm.WeatherAlarm;
import com.exwhythat.mobilization.alarm.WeatherAlarm.UpdateInterval;
import com.exwhythat.mobilization.ui.base.BaseFragment;
import com.exwhythat.mobilization.util.SettingPrefs;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class SettingsFragment extends BaseFragment implements SettingsView {

    public static final String TAG = SettingsFragment.class.getCanonicalName();

    @Inject
    SettingsPresenterImpl presenter;

    RadioGroup rgUpdateInterval;

    public SettingsFragment() {}

    @NonNull
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rgUpdateInterval = ButterKnife.findById(view, R.id.rgUpdateInterval);
        setRadioGroupListener(rgUpdateInterval);
        loadSavedInterval(rgUpdateInterval);
    }

    private void setRadioGroupListener(RadioGroup rg) {
        rg.setOnCheckedChangeListener((radioGroup, idChecked) -> {
            switch (idChecked) {
                case R.id.rb1s:
                    saveNewIntervalAndSetAlarm(UpdateInterval.SECONDS_ONE);
                    break;
                case R.id.rb10s:
                    saveNewIntervalAndSetAlarm(UpdateInterval.SECONDS_TEN);
                    break;
                case R.id.rb1m:
                    saveNewIntervalAndSetAlarm(UpdateInterval.MINUTES_ONE);
                    break;
                case R.id.rb10m:
                    saveNewIntervalAndSetAlarm(UpdateInterval.MINUTES_TEN);
                    break;
                case R.id.rb30m:
                    saveNewIntervalAndSetAlarm(UpdateInterval.MINUTES_THIRTY);
                    break;
                case R.id.rb1h:
                    saveNewIntervalAndSetAlarm(UpdateInterval.HOURS_ONE);
                    break;
                default:
                    break;
            }
        });
    }

    private void saveNewIntervalAndSetAlarm(@UpdateInterval int newInterval) {
        Context context = getContext();
        SettingPrefs.putSettingsUpdateInterval(context, newInterval);
        WeatherAlarm.setAlarm(context, newInterval);
    }

    private void loadSavedInterval(RadioGroup rg) {
        @UpdateInterval int interval = SettingPrefs.getSettingsUpdateInterval(getContext());
        switch (interval) {
            case UpdateInterval.SECONDS_ONE:
                rg.check(R.id.rb1s);
                break;
            case UpdateInterval.SECONDS_TEN:
                rg.check(R.id.rb10s);
                break;
            case UpdateInterval.MINUTES_ONE:
                rg.check(R.id.rb1m);
                break;
            case UpdateInterval.MINUTES_TEN:
                rg.check(R.id.rb10m);
                break;
            case UpdateInterval.MINUTES_THIRTY:
                rg.check(R.id.rb30m);
                break;
            case UpdateInterval.HOURS_ONE:
                rg.check(R.id.rb1h);
                break;
            default:
                rg.check(R.id.rb10s);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
