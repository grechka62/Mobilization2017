package com.exwhythat.mobilization.presenters;

import com.exwhythat.mobilization.interactors.WeatherInteractorImpl;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepositoryImpl;
import com.exwhythat.mobilization.ui.weather.WeatherPresenter;
import com.exwhythat.mobilization.ui.weather.WeatherPresenterImpl;
import com.exwhythat.mobilization.ui.weather.WeatherView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Grechka on 30.07.2017.
 */

public class WeatherPresenterUnitTest {

    @Mock WeatherView view;
    @Mock WeatherInteractorImpl interactor;
    @Mock LocalCityRepositoryImpl cityRepository;
    @Mock City city;
    @Mock WeatherItem weatherItem;

    private WeatherPresenter presenter;

    @Before
    public void onInit() {
        MockitoAnnotations.initMocks(this);
        presenter = new WeatherPresenterImpl(interactor, cityRepository);
        when(cityRepository.getCityInfo(anyString())).thenReturn(Single.just(city));
        /*when(interactor.getWeather(any())).thenReturn(Single.just(weatherItem));
        when(interactor.getCurrentWeather(any())).thenReturn(Single.just(weatherItem));*/
    }

    @Test
    public void shouldBeginWeatherLoading() {
        presenter.onAttach(view);
        verify(view).showLoading();
        verify(cityRepository, only()).getCityInfo(anyString());
        //verify(interactor, only()).getCurrentWeather(any());
    }

    /*@Override
    public void refreshWeather() {
        getMvpView().showLoading();
        showDataFromWeatherRepo(remoteRepo);
    }

    @Override
    public void onPrefsChanged() {
        getMvpView().showLoading();
        showDataFromWeatherRepo(localRepo);
    }

    private void showDataFromWeatherRepo(WeatherRepository weatherRepo) {
        Single<WeatherResponse> currentWeatherSingle = weatherRepo.getCurrentWeather(city.getLocation());

        disposable.dispose();
        disposable = currentWeatherSingle
                .subscribeOn(Schedulers.io())
                .map(WeatherItem::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(WeatherItem item) {
        disposable.dispose();
        item.setCity(city.getName());
        getMvpView().showResult(item);
    }

    private void onError(Throwable throwable) {
        disposable.dispose();
        getMvpView().showError(throwable);
    }*/
}
