package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by exwhythat on 11.07.17.
 */

public interface WeatherPresenter extends BasePresenter<WeatherView> {

    void observeCheckedCity();

    void observeWeather();

    void refreshWeather();

    void obtainWeather(long cityId);
}
