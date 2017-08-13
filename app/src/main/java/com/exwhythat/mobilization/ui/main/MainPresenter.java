package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.ui.base.BasePresenter;

/**
 * Created by exwhythat on 07.07.17.
 */

public interface MainPresenter extends BasePresenter<MainView> {

    void initCities();

    void initCheckedCity();

    void getCheckedCity();

    void observeCheckedCity();

    void observeCity();

    void deleteCity(int id, long checkedCityId);

    void goToAnotherCityWeather(int id);

    void goToCitySelection();

    void goToSettings();

    void goToAbout();
}
