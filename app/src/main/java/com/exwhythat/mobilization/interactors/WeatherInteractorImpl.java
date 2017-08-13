package com.exwhythat.mobilization.interactors;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepositoryImpl;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepositoryImpl;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by Grechka on 11.08.2017.
 */

public class WeatherInteractorImpl implements WeatherInteractor {

    private WeatherRepository remoteRepo;
    private LocalWeatherRepository localRepo;

    @Inject
    public WeatherInteractorImpl(RemoteWeatherRepositoryImpl remoteRepo, LocalWeatherRepositoryImpl localRepo) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public Observable<WeatherItem> getSavedWeather(City city) {
        return Observable.concat(localRepo.getForecast(city),
                localRepo.getCurrentWeather(city).toObservable());
    }

    @Override
    public Observable<WeatherItem> getWeather(City city) {
        List<WeatherItem> weatherList = new ArrayList<>();
        return getSavedWeather(city)
                .doOnNext(weatherList::add)
                .doOnTerminate(() -> {
                    if ((weatherList.size() < 11) ||
                            (weatherList.get(9).getCity() != city.getId())) {
                        updateWeather(city).subscribeOn(Schedulers.io()).subscribe();
                    }
                });
    }

    @Override
    public Observable<WeatherItem> updateWeather(City city) {
        List<WeatherItem> forecast = new ArrayList<>();
        return Observable.concat(remoteRepo.getForecast(city)
                        .map(weatherItem -> addItemToForecast(forecast, weatherItem, city))
                        .flatMap(weatherItem -> {
                            if (forecast.size() == 10) {
                                return putForecast(forecast);
                            } else {
                                return Observable.just(weatherItem);
                            }
                        }),
                remoteRepo.getCurrentWeather(city)
                        .flatMap(weatherItem -> putCurrentWeather(weatherItem, city))
                        .toObservable());
    }

    private Single<WeatherItem> putCurrentWeather(WeatherItem weatherItem, City city) {
        weatherItem.setType(WeatherItem.WeatherTypes.CURRENT);
        weatherItem.setCity(city.getId());
        return localRepo.putCurrentWeather(weatherItem);
    }

    private WeatherItem addItemToForecast(List<WeatherItem> forecast, WeatherItem weatherItem, City city) {
        weatherItem.setCity(city.getId());
        weatherItem.setType(WeatherItem.WeatherTypes.FORECAST);
        forecast.add(weatherItem);
        return weatherItem;
    }

    private Observable<WeatherItem> putForecast(List<WeatherItem> forecast) {
        return localRepo.putForecast(forecast);
    }

    @Override
    public Flowable<DatabaseChange<WeatherItem>> observeWeather() {
        return localRepo.observeWeather();
    }
}
