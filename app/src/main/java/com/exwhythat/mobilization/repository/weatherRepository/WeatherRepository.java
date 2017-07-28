package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.cityResponse.part.Location;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;

import io.reactivex.Single;

/**
 * Created by exwhythat on 16.07.17.
 */

public interface WeatherRepository {

    Single<WeatherResponse> getCurrentWeather(Location location);
}
