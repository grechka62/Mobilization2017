package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.model.part.Location;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by exwhythat on 16.07.17.
 */

public interface WeatherRepository {

    Single<WeatherItem> getCurrentWeather(Location location);

    Observable<WeatherItem> getTodayWeather(Location location);

    Observable<WeatherItem> getForecast(Location location);
}
