package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by exwhythat on 16.07.17.
 */

public interface WeatherRepository {

    Single<WeatherItem> getCurrentWeather(City city);

    Observable<WeatherItem> getForecast(City city);
}
