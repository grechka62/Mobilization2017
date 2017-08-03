package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.cityRepository.CityRepository;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.Single;
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
    private CityRepository cityRepository;
    private City city;

    @Inject
    public WeatherPresenterImpl(RemoteWeatherRepository remoteRepo, LocalWeatherRepository localRepo,
                                LocalCityRepository cityRepository) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
        this.cityRepository = cityRepository;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        getMvpView().showLoading();
        cityRepository.getCityInfo("")
                .subscribe(cityInfo -> city = cityInfo);
        showDataFromWeatherRepo(localRepo);
    }

    @Override
    public void onRefreshData() {
        getMvpView().showLoading();
        showDataFromWeatherRepo(remoteRepo);
    }

    @Override
    public void onPrefsChanged() {
        getMvpView().showLoading();
        showDataFromWeatherRepo(localRepo);
    }

    private void showDataFromWeatherRepo(WeatherRepository weatherRepo) {
        Single<WeatherItem> currentWeatherSingle = weatherRepo.getCurrentWeather(city.getLocation());

        disposable.dispose();
        disposable = currentWeatherSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(WeatherItem item) {
        disposable.dispose();
        item.setCity(city);
        //TODO saveWeather
        getMvpView().showResult(item);
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        getMvpView().showError(throwable);
    }
}
