package com.exwhythat.mobilization.repository.cityRepository;

import com.exwhythat.mobilization.model.CheckedCity;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.model.part.Weather;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import nl.nl2312.rxcupboard2.DatabaseChange;
import nl.nl2312.rxcupboard2.RxDatabase;

/**
 * Created by Grechka on 28.07.2017.
 */

public class LocalCityRepository implements CityRepository {

    private RxDatabase database;

    @Inject
    public LocalCityRepository(RxDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<Prediction> getCitySuggest(String input) {
        return null;
    }

    @Override
    public Single<City> getCityInfo(String placeId) {
        return database.query(City.class, "place_id=?", placeId)
                .first(new City());
    }

    public Single<City> getCheckedCity(long id) {
        return database.query(City.class, "_id=?", Long.toString(id))
                .first(new City());
    }

    @Override
    public Single<CheckedCity> putCity(City city) {
        return database.put(city)
                .delaySubscription(database.query(database.buildQuery(City.class).orderBy("_id desc").limit(1))
                        .doOnNext(item -> city.setId(item.getId() + 1)))
                .flatMap(item -> database.put(new CheckedCity(item.getId())));
    }

    @Override
    public Single<CheckedCity> changeCheckedCity(long id) {
        return database.put(new CheckedCity(id));
    }

    public Observable<City> getAllCities() {
        return database.query(City.class, "_id>?", "0").toObservable();
    }

    public Single<CheckedCity> initCheckedCity() {
        return database.put(new City()).flatMap(city -> database.put(new CheckedCity()));
    }

    public Single<CheckedCity> getCheckedId() {
        return database.query(CheckedCity.class).first(new CheckedCity());
    }

    public Flowable<DatabaseChange<CheckedCity>> observeCheckedCity() {
        return database.changes(CheckedCity.class);
    }

    public Flowable<DatabaseChange<City>> observeCity() {
        return database.changes(City.class);
    }

    public Single<City> deleteCity(int id) {
        return database.delete(City.class, id)
                .flatMap(item -> database.delete(WeatherItem.class, "city_id=?", Integer.toString(id)))
                .flatMap(item -> database.query(database.buildQuery(City.class)
                        .withSelection("_id>?", "0")
                        .orderBy("_id"))
                        .first(new City()));
    }
}
