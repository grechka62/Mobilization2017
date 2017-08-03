package com.exwhythat.mobilization.repository.cityRepository;

import android.content.Context;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.util.CityPrefs;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Grechka on 28.07.2017.
 */

public class LocalCityRepository implements CityRepository {

    private Context context;

    @Inject
    public LocalCityRepository(Context context) {
        this.context = context;
    }
    @Override
    public Observable<Prediction> getCitySuggest(String input) {
        return null;
    }

    @Override
    public Single<City> getCityInfo(String placeId) {
        return Single.just(CityPrefs.getCity(context));
    }

    @Override
    public void putCity(City city) {
        CityPrefs.putCity(context, city);
    }
}
