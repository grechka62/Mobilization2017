package com.exwhythat.mobilization.repository.weatherRepository;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import nl.nl2312.rxcupboard2.RxDatabase;
import nl.qbusict.cupboard.Cupboard;

/**
 * Created by exwhythat on 16.07.17.
 */

public class LocalWeatherRepository implements WeatherRepository {

    private RxDatabase database;
    private Calendar calendar;
    private long time;

    @Inject
    public LocalWeatherRepository(Cupboard cupboard, RxDatabase database, Calendar calendar) {
        this.database = database;
        this.calendar = calendar;
    }

    @Override
    public Single<WeatherItem> getCurrentWeather(City city) {
        time = calendar.getTimeInMillis();
        return database.query(database.buildQuery(WeatherItem.class)
        .withSelection("city_id=?", "0"))
                //.filter(item -> item.getWeatherTime()>=time-10800000)
                .first(new WeatherItem());
    }

    /*return database.query(WeatherItem.class,
            "'city_id'=? and " +
            "(('type'=? and 'weather_time'>=?) or " +
            "('type'=? and 'weather_time'>=? and 'weather_time'<?))",
            Long.toString(city.getId()), "0", Long.toString(time-10800000),
            "1", Long.toString(time-10800000), Long.toString(time+10800000))
            .first(weatherItem);*/

    @Override
    public void putCurrentWeather(WeatherItem weatherItem) {
        database.put(weatherItem).subscribe();
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
    public void putWeatherList(List<WeatherItem> weather) {
        database.put(weather);
    }

    /*private Flowable<City> obtainCity(Location location) {
        return database.query(City.class, "latitude = ? and longitude = ?",
                Double.toString(location.getLat()), Double.toString(location.getLng()))
                .doOnNext(item -> city = item);
    }*/
}