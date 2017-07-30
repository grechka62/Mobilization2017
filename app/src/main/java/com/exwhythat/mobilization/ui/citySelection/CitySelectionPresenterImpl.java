package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.network.suggestResponse.part.Prediction;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepository;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepository;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Grechka on 23.07.2017.
 */

public class CitySelectionPresenterImpl extends BasePresenterImpl<CitySelectionView>
        implements CitySelectionPresenter {

    private Disposable inputObserve = new CompositeDisposable();
    private Disposable disposable = new CompositeDisposable();

    private RemoteWeatherRepository remoteWeatherRepo;
    private RemoteCityRepository remoteRepo;
    private LocalCityRepository localRepo;

    @Inject
    CitySelectionPresenterImpl(RemoteCityRepository remoteRepo, LocalCityRepository localRepo,
                               RemoteWeatherRepository remoteWeatherRepository) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
        this.remoteWeatherRepo = remoteWeatherRepository;
    }

    @Override
    public void onTextChanges(Observable<CharSequence> input) {
        inputObserve = input
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getCitySuggest);
    }

    public void getCitySuggest(CharSequence input) {
        getMvpView().clearSuggestions();
        getMvpView().showLoading();
        disposable.dispose();
        disposable = remoteRepo.getCitySuggest(input.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showCitySuggest, this::onError);
    }

    @Override
    public void onSuggestClick(CharSequence placeId) {
        disposable.dispose();
        disposable = remoteRepo.getCityInfo(placeId.toString())
                .doOnSuccess(cityInfo -> localRepo.putCity(cityInfo))
                .flatMap(cityInfo -> remoteWeatherRepo.getCurrentWeather(cityInfo.getLocation()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(WeatherResponse response) {
        disposable.dispose();
        getMvpView().showWeather();
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        getMvpView().showError(throwable);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        inputObserve.dispose();
        disposable.dispose();
    }

    private void showCitySuggest(Prediction suggest) {
        getMvpView().showCitySuggest(suggest);
    }
}
