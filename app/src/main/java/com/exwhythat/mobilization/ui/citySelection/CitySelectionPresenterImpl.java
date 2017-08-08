package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.model.CheckedCity;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepository;
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

    private RemoteCityRepository remoteRepo;
    private LocalCityRepository localRepo;

    @Inject
    public CitySelectionPresenterImpl(RemoteCityRepository remoteRepo, LocalCityRepository localRepo) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public void onTextChanges(Observable<CharSequence> input) {
        inputObserve = input
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getCitySuggest);
    }

    private void getCitySuggest(CharSequence input) {
        CitySelectionView v = getMvpView();
        if (v != null) {
            getMvpView().clearSuggestions();
            getMvpView().showLoading();
        }
        disposable.dispose();
        disposable = remoteRepo.getCitySuggest(input.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showCitySuggest, this::onError);
    }

    @Override
    public void onSuggestClick(CharSequence placeId) {
        disposable.dispose();
        disposable = localRepo.getCityInfo(placeId.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {
                        city.setPlaceId(placeId.toString());
                        onSuccess(city, TYPE_LOCAL);},
                        this::onError);
    }

    private void getCityInfoFromHttp(String placeId) {
        disposable.dispose();
        disposable = remoteRepo.getCityInfo(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> onSuccess(city, TYPE_REMOTE), this::onError);
    }

    private void onSuccess(City city, int type) {
        disposable.dispose();
        if (type == TYPE_REMOTE) {
            localRepo.putCity(city)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::showWeather);
        } else {
            if (city.getName() == null) {
                getCityInfoFromHttp(city.getPlaceId());
            } else {
                localRepo.changeCheckedCity(city.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::showWeather);
            }
        }
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        if (getMvpView() != null) getMvpView().showError(throwable);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        inputObserve.dispose();
        disposable.dispose();
    }

    private void showCitySuggest(Prediction suggest) {
        if (getMvpView() != null) getMvpView().showCitySuggest(suggest);
    }

    private void showWeather(CheckedCity city) {
        if (getMvpView() != null) getMvpView().showWeather();
    }
}
