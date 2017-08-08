package com.exwhythat.mobilization.repository.cityRepository;

import com.exwhythat.mobilization.model.CheckedCity;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Grechka on 27.07.2017.
 */

public interface CityRepository {
    Observable<Prediction> getCitySuggest(String input);
    Single<City> getCityInfo(String placeId);
    Single<CheckedCity> putCity(City city);
    Single<CheckedCity> changeCheckedCity(long id);
}
