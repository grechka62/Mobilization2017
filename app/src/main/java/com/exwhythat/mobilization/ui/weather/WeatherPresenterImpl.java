package com.exwhythat.mobilization.ui.weather;

import repository.LocalWeatherRepository;
import repository.RemoteWeatherRepository;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.RestApi;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import repository.WeatherRepository;

/**
 * Created by exwhythat on 11.07.17.
 */

public class WeatherPresenterImpl<V extends WeatherView> extends BasePresenterImpl<V>
        implements WeatherPresenter<V> {

    private Disposable disposable = new CompositeDisposable();

    private WeatherRepository remoteRepo;
    private WeatherRepository localRepo;

    @Inject
    public WeatherPresenterImpl(RemoteWeatherRepository remoteRepo, LocalWeatherRepository localRepo) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public void onRefreshData() {
        getMvpView().showLoading();

        Single<WeatherItem> currentWeatherSingle = remoteRepo.getCurrentWeather();

        disposable.dispose();
        disposable = currentWeatherSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    @Override
    public void onPrefsChanged() {
        getMvpView().showLoading();

        Single<WeatherItem> currentWeatherSingle = localRepo.getCurrentWeather();

        disposable.dispose();
        disposable = currentWeatherSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(WeatherItem item) {
        disposable.dispose();
        getMvpView().showResult(item);
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        getMvpView().showError(throwable);
    }
}
