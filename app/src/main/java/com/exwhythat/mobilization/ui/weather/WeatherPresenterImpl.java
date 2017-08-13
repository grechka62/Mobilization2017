package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.interactors.WeatherInteractor;
import com.exwhythat.mobilization.interactors.WeatherInteractorImpl;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepositoryImpl;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

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

    private Disposable disposable = new CompositeDisposable();
    private Disposable checkedCityDisposable = new CompositeDisposable();
    private Disposable weatherDisposable = new CompositeDisposable();

    private LocalCityRepository cityRepository;
    private WeatherInteractor weatherInteractor;

    private long checkedCityId = 0;

    @Inject
    public WeatherPresenterImpl(WeatherInteractorImpl weatherInteractor, LocalCityRepositoryImpl cityRepository) {
        super();
        this.weatherInteractor = weatherInteractor;
        this.cityRepository = cityRepository;
    }

    @Override
    public void observeCheckedCity() {
        checkedCityDisposable = cityRepository.observeCheckedCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    checkedCityId = change.entity().getCityId();
                    obtainWeather(checkedCityId);
                }, this::onError);
    }

    private void onError(Throwable throwable) {
        WeatherView v = getMvpView();
        if (v != null) v.showError(throwable);
    }

    @Override
    public void observeWeather() {
        weatherDisposable = weatherInteractor.observeWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    if (change.getClass() != DatabaseChange.DatabaseDelete.class) {
                        showWeather(change.entity());
                    }
                }, this::onError);
    }

    @Override
    public void obtainWeather(long cityId) {
        disposable.dispose();
        if (checkedCityId == 0) checkedCityId = cityId;
        WeatherView v = getMvpView();
        if (v != null) v.showLoading();
        disposable = cityRepository.getCheckedCity(checkedCityId)
                .flatMapObservable(weatherInteractor::getWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showWeather, this::onError);
    }

    private void showWeather(WeatherItem item) {
        if (item.getCity() == checkedCityId) {
            if (item.getType() == WeatherItem.WeatherTypes.CURRENT) {
                showResult(item);
            } else {
                showForecastItem(item);
            }
        }
    }

    @Override
    public void refreshWeather() {
        disposable.dispose();
        disposable = cityRepository.getCheckedCity(checkedCityId)
                .flatMapObservable(weatherInteractor::updateWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                }, this::onUpdatingError);
    }

    private void onUpdatingError(Throwable t) {
        disposable.dispose();
        disposable = cityRepository.getCheckedCity(checkedCityId)
                .flatMapObservable(weatherInteractor::getSavedWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showWeather, this::onError);
        onError(t);
    }

    private void showResult(WeatherItem weatherItem) {
        WeatherView v = getMvpView();
        if (v != null) v.showResult(weatherItem);
    }

    private void showForecastItem(WeatherItem weatherItem) {
        WeatherView v = getMvpView();
        if (v != null) v.showForecastItem(weatherItem);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
        checkedCityDisposable.dispose();
        weatherDisposable.dispose();
    }
}
