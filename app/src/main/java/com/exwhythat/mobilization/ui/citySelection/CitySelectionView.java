package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.ui.base.BaseView;

/**
 * Created by Grechka on 23.07.2017.
 */

public interface CitySelectionView extends BaseView {

    void clearSuggestions();
    void showCitySuggest(Prediction suggest);
    void showLoading();
    void showWeather();
    void showError(Throwable throwable);
}
