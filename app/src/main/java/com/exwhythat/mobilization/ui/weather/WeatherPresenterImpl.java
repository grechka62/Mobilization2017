package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by exwhythat on 11.07.17.
 */

public class WeatherPresenterImpl extends BasePresenterImpl<WeatherView>
        implements WeatherPresenter {

    private Disposable disposable = new CompositeDisposable();

    private WeatherRepository remoteRepo;
    private WeatherRepository localRepo;
    private LocalCityRepository cityRepository;
    private City city;
    private long checkedCityId = 0;

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
        cityRepository.observeCheckedCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    checkedCityId = change.entity().getCityId();
                    onPrefsChanged(checkedCityId);
                });
    }

    @Override
    public void observeWeather() {
        cityRepository.observeWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                        if (change.entity().getCity() == checkedCityId) {
                            onSuccess(change.entity(), TYPE_LOCAL);
                        }
                });
    }

    @Override
    public void onRefreshData() {
        WeatherView v = getMvpView();
        if (v != null) v.showLoading();
        showDataFromWeatherRepo(remoteRepo, TYPE_REMOTE, checkedCityId);
    }

    @Override
    public void onPrefsChanged(long cityId) {
        if (checkedCityId == 0) checkedCityId = cityId;
        WeatherView v = getMvpView();
        if (v != null) v.showLoading();
        showDataFromWeatherRepo(localRepo, TYPE_LOCAL, checkedCityId);
    }

    private void showDataFromWeatherRepo(WeatherRepository weatherRepo, int type, long cityId) {
        disposable.dispose();
        disposable = cityRepository.getCheckedCity(cityId)
                .doOnSuccess(item -> city = item)
                .flatMap(weatherRepo::getCurrentWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> onSuccess(item, type), this::onError);
    }

    private void onSuccess(WeatherItem item, int type) {
        disposable.dispose();
        if (type == TYPE_REMOTE) {
            item.setCity(city.getId());
            localRepo.putCurrentWeather(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        } else {
            if (item.getCity() != city.getId()) {
                showDataFromWeatherRepo(remoteRepo, TYPE_REMOTE, checkedCityId);
            } else {
                showResult(item);
            }
        }
    }

    private void showResult(WeatherItem weatherItem) {
        WeatherView v = getMvpView();
        if (v != null) v.showResult(weatherItem);
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        WeatherView v = getMvpView();
        if (v != null) v.showError(throwable);
    }
}
