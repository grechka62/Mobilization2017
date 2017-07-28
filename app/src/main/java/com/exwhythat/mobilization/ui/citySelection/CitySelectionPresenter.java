package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.ui.base.BasePresenter;

import io.reactivex.Observable;

/**
 * Created by Grechka on 23.07.2017.
 */

public interface CitySelectionPresenter extends BasePresenter<CitySelectionView> {
    void observeInput(Observable<CharSequence> input);
    void getCitySuggest(CharSequence input);
    void getCityInfo(CharSequence placeId);
}
