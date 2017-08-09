package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by exwhythat on 11.07.17.
 */

public class WeatherPresenterImpl extends BasePresenterImpl<WeatherView>
        implements WeatherPresenter {

    //private Disposable disposable = new CompositeDisposable();
    private Disposable checkedCityDisposable = new CompositeDisposable();
    private Disposable weatherDisposable = new CompositeDisposable();
    private Disposable currentDisposable = new CompositeDisposable();
    private Disposable forecastDisposable = new CompositeDisposable();

    private WeatherRepository remoteRepo;
    private WeatherRepository localRepo;
    private LocalCityRepository cityRepository;
    private City city;
    private long checkedCityId = 0;
    private List<WeatherItem> forecast = new ArrayList<>();

    @Inject
    public WeatherPresenterImpl(RemoteWeatherRepository remoteRepo, LocalWeatherRepository localRepo,
                                LocalCityRepository cityRepository) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
        this.cityRepository = cityRepository;
    }


    @Override
    public void observeCheckedCity() {
        checkedCityDisposable = cityRepository.observeCheckedCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    checkedCityId = change.entity().getCityId();
                    onPrefsChanged(checkedCityId);
                }, Throwable::printStackTrace);
    }

    @Override
    public void observeWeather() {
        final WeatherItem[] item = new WeatherItem[1];
        weatherDisposable = localRepo.observeWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                        item[0] = change.entity();
                        if ((change.entity().getCity() == checkedCityId)
                                && (change.getClass() != DatabaseChange.DatabaseDelete.class)) {
                            if (item[0].getType() == WeatherItem.WeatherTypes.CURRENT) {
                                onLocalSuccess(change.entity());
                            } else {
                                showForecastItem(item[0]);
                            }
                        }
                }, this::onLocalForecastError/*, this::onLocalForecastSuccess*/);
    }

    @Override
    public void onRefreshData() {
        WeatherView v = getMvpView();
        if (v != null) v.showLoading();
        //showDataFromWeatherRepo(remoteRepo, TYPE_REMOTE, checkedCityId);
        showDataFromRemoteWeatherRepo();
        showForecastFromRemoteWeatherRepo();
    }

    @Override
    public void onPrefsChanged(long cityId) {
        if (checkedCityId == 0) checkedCityId = cityId;
        WeatherView v = getMvpView();
        if (v != null) v.showLoading();
        //showDataFromWeatherRepo(localRepo, TYPE_LOCAL, checkedCityId);
        showDataFromLocalWeatherRepo();
        showForecastFromLocalWeatherRepo();
    }

    /*private void showDataFromWeatherRepo(WeatherRepository weatherRepo, int type, long cityId) {
        disposable.dispose();
        disposable = cityRepository.getCheckedCity(cityId)
                .doOnSuccess(item -> city = item)
                .flatMap(weatherRepo::getCurrentWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> onSuccess(item, type), this::onError);
    }*/

    private void showDataFromLocalWeatherRepo() {
        currentDisposable.dispose();
        currentDisposable = cityRepository.getCheckedCity(checkedCityId)
                .doOnSuccess(item -> city = item)
                .flatMap(localRepo::getCurrentWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLocalSuccess, this::onError);
    }

    private void onLocalSuccess(WeatherItem item) {
        currentDisposable.dispose();
        if (item.getCity() != city.getId()) {
            showDataFromRemoteWeatherRepo();
        } else {
            showResult(item);
        }
    }

    private void showDataFromRemoteWeatherRepo() {
        currentDisposable.dispose();
        currentDisposable = cityRepository.getCheckedCity(checkedCityId)
                .doOnSuccess(item -> city = item)
                .flatMap(remoteRepo::getCurrentWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRemoteSuccess, this::onError);
    }

    private void onRemoteSuccess(WeatherItem item) {
        currentDisposable.dispose();
        item.setCity(city.getId());
        currentDisposable = localRepo.putCurrentWeather(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherItem -> {}, Throwable::printStackTrace);
    }

    private void showForecastFromLocalWeatherRepo() {
        forecast.clear();
        forecastDisposable.dispose();
        forecastDisposable = cityRepository.getCheckedCity(checkedCityId)
                .doOnSuccess(item -> city = item)
                .flatMapObservable(localRepo::getForecast)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forecast::add, this::onLocalForecastError, this::onLocalForecastSuccess);
    }

    private void onLocalForecastSuccess() {
        forecastDisposable.dispose();
        if (forecast.isEmpty() || (forecast.get(0).getCity() != city.getId())) {
            showForecastFromRemoteWeatherRepo();
        } else {
            showForecast();
        }
    }

    private void onLocalForecastError(Throwable t) {
        showForecastFromRemoteWeatherRepo();
        t.printStackTrace();
    }

    private void showForecastFromRemoteWeatherRepo() {
        forecast.clear();
        forecastDisposable.dispose();
        forecastDisposable = cityRepository.getCheckedCity(checkedCityId)
                .doOnSuccess(item -> city = item)
                .flatMapObservable(remoteRepo::getForecast)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItemToForecast, this::onError, this::onRemoteForecastSuccess);
    }

    private void addItemToForecast(WeatherItem item) {
        item.setCity(city.getId());
        item.setType(WeatherItem.WeatherTypes.FORECAST);
        forecast.add(item);
    }

    private void onRemoteForecastSuccess() {
        currentDisposable.dispose();
        currentDisposable = localRepo.putWeatherList(forecast)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherItem -> {}, Throwable::printStackTrace);
    }

    /*private void onSuccess(WeatherItem item, int type) {
        disposable.dispose();
        if (type == TYPE_REMOTE) {
            item.setCity(city.getId());
            disposable = localRepo.putCurrentWeather(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        } else {
            if (item.getCity() != city.getId()) {
                showDataFromWeatherRepo(remoteRepo, TYPE_REMOTE, checkedCityId);
            } else {
                showResult(item);
            }
        }
    }*/

    private void showResult(WeatherItem weatherItem) {
        WeatherView v = getMvpView();
        if (v != null) v.showResult(weatherItem);
    }

    private void showForecast() {
        WeatherView v = getMvpView();
        if (v != null) v.showForecast(forecast);
    }

    private void showForecastItem(WeatherItem weatherItem) {
        WeatherView v = getMvpView();
        if (v != null) v.showForecastItem(weatherItem);
    }

    private void onError(Throwable throwable) {
        currentDisposable.dispose();
        forecastDisposable.dispose();
        WeatherView v = getMvpView();
        if (v != null) v.showError(throwable);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currentDisposable.dispose();
        checkedCityDisposable.dispose();
        weatherDisposable.dispose();
    }
}
