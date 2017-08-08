package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.ui.base.BaseView;

import java.util.List;

/**
 * Created by exwhythat on 07.07.17.
 */

public interface MainView extends BaseView {

    void showWeather();

    void showAbout();

    void showSettings();

    void showCitySelection();

    void setCitiesOnDrawer(List<City> cities, long checkedCity);

    void setCheckedCity(int id);

    void addCity(City city);

    void deleteCity(int itemId);
}
