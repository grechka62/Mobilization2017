package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by exwhythat on 07.07.17.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    void onDrawerWeatherClick();
    void onDrawerAboutClick();
    void onDrawerSettingsClick();
    void onDrawerCitySelectionClick();
}
