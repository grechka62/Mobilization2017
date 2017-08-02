package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by exwhythat on 11.07.17.
 */

public interface WeatherPresenter extends BasePresenter<WeatherView> {
    void onRefreshData();
    void onPrefsChanged();
}
