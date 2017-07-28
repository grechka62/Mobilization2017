package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.model.CityInfo;
import com.exwhythat.mobilization.network.suggestResponse.part.Prediction;
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
    CitySelectionPresenterImpl(RemoteCityRepository remoteRepo, LocalCityRepository localRepo) {
        super();
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public void onTextChanges(Observable<CharSequence> input) {
        inputObserve = input.subscribe(this::getCitySuggest);
    }

    public void getCitySuggest(CharSequence input) {
        getMvpView().clearSuggestions();
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(CityInfo cityInfo) {
        disposable.dispose();
        localRepo.putCity(cityInfo);
        localRepo.updateWeather();
        getMvpView().showWeather(cityInfo);
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
