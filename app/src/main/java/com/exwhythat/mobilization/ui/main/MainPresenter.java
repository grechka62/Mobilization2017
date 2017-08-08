package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by exwhythat on 07.07.17.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    void initCities();
    void initCheckedCity();
    void observeCheckedCity();
    void observeCity();
    void onDrawerWeatherClick(int id);
    void onDrawerAboutClick();
    void onDrawerSettingsClick();
    void onDrawerCitySelectionClick();
    void onDrawerCityDeletingClick(int id, long checkedCityId);
}
