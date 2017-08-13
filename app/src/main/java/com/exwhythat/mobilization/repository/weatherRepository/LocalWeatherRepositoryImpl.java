package com.exwhythat.mobilization.repository.weatherRepository;

import com.annimon.stream.Stream;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.DatabaseChange;
import nl.nl2312.rxcupboard2.RxDatabase;

/**
 * Created by exwhythat on 16.07.17.
 */

public class LocalWeatherRepositoryImpl implements LocalWeatherRepository {

    private RxDatabase database;
    private Calendar calendar;
    private long time;

    @Inject
    public LocalWeatherRepositoryImpl(RxDatabase database, Calendar calendar) {
        this.database = database;
        this.calendar = calendar;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather(City city) {
        time = calendar.getTimeInMillis();
        return database.query(WeatherItem.class,
                "city_id=? and type=? and weather_time>=?",
                Long.toString(city.getId()),
                Integer.toString(WeatherItem.WeatherTypes.CURRENT),
                Long.toString(time / 1000 - 10800))
                .first(new WeatherItem());
    }

    @Override
    public Single<WeatherItem> putCurrentWeather(WeatherItem weatherItem) {
        return setWeatherIdFromDatabase(weatherItem).flatMap(database::put);
    }

    private Single<WeatherItem> setWeatherIdFromDatabase(WeatherItem weatherItem) {
        return Observable.concat(getExistingCurrentWeatherId(weatherItem.getCity()),
                getNewCurrentWeatherId())
                .first(new WeatherItem())
                .map(item -> {
                    weatherItem.setId(item.getId());
                    return weatherItem;
                });
    }

    private Observable<WeatherItem> getExistingCurrentWeatherId(long cityId) {
        return database.query(WeatherItem.class,
                "city_id=? and type=?", Long.toString(cityId),
                Integer.toString(WeatherItem.WeatherTypes.CURRENT))
                .toObservable();
    }

    private Observable<WeatherItem> getNewCurrentWeatherId() {
        return database.query(database.buildQuery(WeatherItem.class).orderBy("_id desc"))
                .map(item -> {
                    item.setId(item.getId() + 1);
                    return item;
                }).toObservable();
    }

    @Override
    public Observable<WeatherItem> getForecast(City city) {
        time = calendar.getTimeInMillis();
        return database.query(WeatherItem.class,
                "city_id=? and type=? and weather_time>?",
                Long.toString(city.getId()),
                Integer.toString(WeatherItem.WeatherTypes.FORECAST),
                Long.toString(time / 1000 - 86400))
                .toObservable();
    }

    @Override
    public Observable<WeatherItem> putForecast(List<WeatherItem> forecast) {
        final long uniqueCount = Stream.of(forecast).distinctBy(WeatherItem::getCity).count();

        if (uniqueCount > 1) {
            throw new IllegalArgumentException();
        }

        long id[] = new long[1];
        return clearForecastForCity(forecast.get(0).getCity())
                .flatMapObservable(curWeatherId -> {
                    id[0] = curWeatherId.getId();
                    return Observable.fromIterable(forecast)
                            .flatMap(weatherItem -> {
                                weatherItem.setId(++id[0]);
                                return database.put(weatherItem).toObservable();
                            });
                });
    }

    private Single<WeatherItem> clearForecastForCity(long cityId) {
        return database.delete(WeatherItem.class, "city_id=? and type=?", Long.toString(cityId), "2")
                .flatMap(o -> database.query(
                        database.buildQuery(WeatherItem.class)
                                .orderBy("_id desc"))
                        .first(new WeatherItem()));
    }

    @Override
    public Flowable<DatabaseChange<WeatherItem>> observeWeather() {
        return database.changes(WeatherItem.class);
    }
}