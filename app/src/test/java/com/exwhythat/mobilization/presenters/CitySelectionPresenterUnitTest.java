package com.exwhythat.mobilization.presenters;

import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepositoryImpl;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepositoryImpl;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionPresenter;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionPresenterImpl;
import com.exwhythat.mobilization.ui.citySelection.CitySelectionView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;

/**
 * Created by Grechka on 30.07.2017.
 */

public class CitySelectionPresenterUnitTest {

    @Mock CitySelectionView view;
    @Mock
    RemoteCityRepositoryImpl remoteRepo;
    @Mock
    LocalCityRepositoryImpl localRepo;

    private CitySelectionPresenter presenter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenter = new CitySelectionPresenterImpl(remoteRepo, localRepo);
    }

    @Test
    public void shouldLoadSuggestions() {
        presenter.observeCityInput(Observable.just(""));
        verify(view).clearSuggestions();
        verify(view).showLoading();
    }

    /*private void getCitySuggest(CharSequence input) {
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
                .doOnSuccess(city -> localRepo.putCity(city))
                .flatMap(city -> remoteWeatherRepo.getCurrentWeather(city.getLocation()))
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
    }*/
}
