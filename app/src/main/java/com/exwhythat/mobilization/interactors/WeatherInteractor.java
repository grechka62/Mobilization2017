package com.exwhythat.mobilization.interactors;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by Grechka on 10.08.2017.
 */

public interface WeatherInteractor {

    Observable<WeatherItem> getSavedWeather(City city);

    Observable<WeatherItem> getWeather(City city);

    Observable<WeatherItem> updateWeather(City city);

    Flowable<DatabaseChange<WeatherItem>> observeWeather();
}
