package com.exwhythat.mobilization.repository.cityRepository;

import com.exwhythat.mobilization.model.CityInfo;
import com.exwhythat.mobilization.network.cityResponse.part.Location;
import com.exwhythat.mobilization.network.suggestResponse.part.Prediction;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Grechka on 27.07.2017.
 */

public interface CityRepository {
    Observable<Prediction> getCitySuggest(String input);
    Single<CityInfo> getCityInfo(String placeId);
    void putCity(CityInfo city);
    void updateWeather();
}
