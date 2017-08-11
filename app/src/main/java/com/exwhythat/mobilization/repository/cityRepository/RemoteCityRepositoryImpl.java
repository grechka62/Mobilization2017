package com.exwhythat.mobilization.repository.cityRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.network.CityApi;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.network.suggestResponse.SuggestResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Grechka on 25.07.2017.
 */

public class RemoteCityRepositoryImpl implements CityRepository {

    private CityApi cityApi;

    @Inject
    public RemoteCityRepositoryImpl(CityApi cityApi) {
        this.cityApi = cityApi;
    }

    @Override
    public Observable<Prediction> getCitySuggest(String input) {
        return cityApi
                .getCitySuggest(input, CityApi.CITY_PLACE_TYPES, CityApi.CITY_API_KEY_VALUE)
                .map(SuggestResponse::getPredictions)
                .flatMap(Observable::fromIterable);
    }

    @Override
    public Single<City> getCityInfo(String placeId) {
        return cityApi
                .getCityInfo(placeId, CityApi.CITY_API_KEY_VALUE)
                .map(City::new);
    }
}