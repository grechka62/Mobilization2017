package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by exwhythat on 11.07.17.
 */

public interface WeatherPresenter<V extends WeatherView> extends BasePresenter<V> {
    void onRefreshData();
    void onPrefsChanged();
}
