package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.ui.base.BaseView;

/**
 * Created by exwhythat on 11.07.17.
 */

public interface WeatherView extends BaseView {

    void showLoading();

    void hideLoading();

    void showResult(WeatherItem item);

    void showError();
}
