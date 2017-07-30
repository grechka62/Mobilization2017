package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.ui.base.BasePresenter;

import io.reactivex.Observable;

/**
 * Created by Grechka on 23.07.2017.
 */

public interface CitySelectionPresenter extends BasePresenter<CitySelectionView> {
    void onTextChanges(Observable<CharSequence> input);
    void onSuggestClick(CharSequence placeId);
}
