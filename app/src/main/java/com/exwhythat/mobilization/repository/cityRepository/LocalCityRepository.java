package com.exwhythat.mobilization.repository.cityRepository;

import com.exwhythat.mobilization.model.CheckedCity;
import com.exwhythat.mobilization.model.City;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by Grechka on 27.07.2017.
 */

public interface LocalCityRepository {

    Single<City> getCityInfo(String placeId);

    Single<City> getCheckedCity(long id);

    Single<CheckedCity> putCity(City city);

    Single<CheckedCity> changeCheckedCity(long id);

    Observable<City> getAllCities();

    Single<CheckedCity> initCheckedCity();

    Single<CheckedCity> getCheckedId();

    Flowable<DatabaseChange<CheckedCity>> observeCheckedCity();

    Flowable<DatabaseChange<City>> observeCity();

    Single<City> deleteCity(int id);
}
