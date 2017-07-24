package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by Grechka on 23.07.2017.
 */

public interface CitySelectionPresenter<V extends CitySelectionView> extends BasePresenter<V> {
    void onInputChanged();
    void onCitySelected();
}
