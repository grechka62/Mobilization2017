package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.RxDatabase;

/**
 * Created by exwhythat on 16.07.17.
 */

public class LocalWeatherRepository implements WeatherRepository {

    private RxDatabase database;
    private Calendar calendar;
    private long time;

    @Inject
    public LocalWeatherRepository(RxDatabase database, Calendar calendar) {
        this.database = database;
        this.calendar = calendar;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather(City city) {
        time = calendar.getTimeInMillis();
        return database.query(WeatherItem.class,
                "city_id=? and " +
                        "((type=? and weather_time>=?) or " +
                        "(type=? and weather_time>=? and weather_time<?))",
                Long.toString(city.getId()), "0", Long.toString(time-10800000),
                "1", Long.toString(time-10800000), Long.toString(time+10800000))
                .first(new WeatherItem());
    }

    @Override
    public Single<WeatherItem> putCurrentWeather(WeatherItem weatherItem) {
        return database.put(weatherItem)
                .delaySubscription(Observable.concat(
                        database.query(WeatherItem.class,
                                "type=0 and city_id=?", Long.toString(weatherItem.getCity())).toObservable(),
                        database.query(database.buildQuery(WeatherItem.class)
                                .orderBy("_id desc")).toObservable())
                        .first(new WeatherItem())
                        .map(item -> {
                            if ((item.getCity() == weatherItem.getCity()) && (item.getType() == 0)) {
                                weatherItem.setId(item.getId());
                            } else {
                                weatherItem.setId(item.getId() + 1);
                            }
                            return weatherItem;
                        }));
    }

    @Override
    public Observable<WeatherItem> getTodayWeather(City city) {
        time = calendar.getTimeInMillis();
        return database.query(WeatherItem.class,
                        "city_id=? and type=? and weather_time>=? and weather_time<?",
                        Long.toString(city.getId()), "1",
                        Long.toString(time-10800000), Long.toString(time+10800000))
                .toObservable();
    }

    @Override
    public Observable<WeatherItem> getForecast(City city) {
        time = calendar.getTimeInMillis();
        return database.query(WeatherItem.class,
                "city_id=? and type=? and weather_time>=?",
                Long.toString(city.getId()), "2", Long.toString(time))
                .toObservable();
    }

    @Override
    public Observable<WeatherItem> putWeatherList(List<WeatherItem> weatherList) {
        return Observable.fromIterable(weatherList).doOnNext(database::put);
    }
}