package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.repository.cityRepository.CityRepository;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepositoryImpl;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepositoryImpl;
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

    private CityRepository remoteRepo;
    private LocalCityRepository localRepo;

    @Inject
    public CitySelectionPresenterImpl(RemoteCityRepositoryImpl remoteRepo, LocalCityRepositoryImpl localRepo) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public void observeCityInput(Observable<CharSequence> input) {
        inputObserve = input
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getCitySuggest);
    }

    private void getCitySuggest(CharSequence input) {
        CitySelectionView v = getMvpView();
        if (v != null) {
            v.clearSuggestions();
            v.showLoading();
        }

        disposable.dispose();
        disposable = remoteRepo.getCitySuggest(input.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showCitySuggest, this::onError);
    }

    private void showCitySuggest(Prediction suggest) {
        if (getMvpView() != null) getMvpView().showCitySuggest(suggest);
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        if (getMvpView() != null) getMvpView().showError(throwable);
    }

    @Override
    public void chooseCity(CharSequence placeId) {
        disposable.dispose();
        disposable = localRepo.getCityInfo(placeId.toString())
                .flatMap(city -> {
                    if (city.getName() == null) {
                        return remoteRepo.getCityInfo(placeId.toString())
                                .flatMap(localRepo::putCity);
                    } else {
                        city.setPlaceId(placeId.toString());
                        return localRepo.changeCheckedCity(city.getId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> showWeather(), this::onError);
    }

    private void showWeather() {
        if (getMvpView() != null) getMvpView().showWeather();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        inputObserve.dispose();
        disposable.dispose();
    }
}
