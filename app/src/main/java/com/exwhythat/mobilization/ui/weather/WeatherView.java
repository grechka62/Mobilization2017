package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.ui.base.BaseView;

import java.util.List;

/**
 * Created by exwhythat on 11.07.17.
 */

public interface WeatherView extends BaseView {

    void showLoading();

    void showResult(WeatherItem item);

    void showForecast(List<WeatherItem> forecast);

    void showForecastItem(WeatherItem weatherItem);

    void showError(Throwable throwable);
}
