package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.model.CityInfo;
import com.exwhythat.mobilization.network.suggestResponse.part.Prediction;
import com.exwhythat.mobilization.ui.base.BaseView;

/**
 * Created by Grechka on 23.07.2017.
 */

public interface CitySelectionView extends BaseView {

    void clearSuggestions();
    void showCitySuggest(Prediction suggest);
    void saveNewCity(CityInfo cityInfo);
}
