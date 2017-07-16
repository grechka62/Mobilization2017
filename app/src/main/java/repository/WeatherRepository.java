package repository;

import com.exwhythat.mobilization.model.WeatherItem;

import io.reactivex.Single;

/**
 * Created by exwhythat on 16.07.17.
 */

public interface WeatherRepository {

    Single<WeatherItem> getCurrentWeather();
}
