package com.exwhythat.mobilization.repository.cityRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Grechka on 10.08.2017.
 */

public interface CityRepository {

    Observable<Prediction> getCitySuggest(String input);

    Single<City> getCityInfo(String placeId);
}
