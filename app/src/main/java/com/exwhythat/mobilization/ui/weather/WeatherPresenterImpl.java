package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by exwhythat on 11.07.17.
 */

public class WeatherPresenterImpl<V extends WeatherView> extends BasePresenterImpl<V>
        implements WeatherPresenter<V> {

    @Inject
    public WeatherPresenterImpl() {
        super();
    }
}
