package com.exwhythat.mobilization;

import com.exwhythat.mobilization.interactors.WeatherInteractor;
import com.exwhythat.mobilization.interactors.WeatherInteractorImpl;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepositoryImpl;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import nl.nl2312.rxcupboard2.DatabaseChange;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Grechka on 13.08.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class WeatherInteractorImplUnitTest {

    @Mock
    RemoteWeatherRepositoryImpl remoteRepo;
    @Mock
    LocalWeatherRepositoryImpl localRepo;

    private WeatherInteractor interactor;
    private City city = new City();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        interactor = new WeatherInteractorImpl(remoteRepo, localRepo);
        city.setId(1);
    }

    @Test
    public void shouldReturnForecastAndCurrentWeather_whenHasSavedWeather() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        item.setType(WeatherItem.WeatherTypes.FORECAST);
        weatherList.add(item);
        weatherList.add(item);
        when(localRepo.getForecast(city)).thenReturn(Observable.just(item, item));
        item = new WeatherItem();
        item.setType(WeatherItem.WeatherTypes.CURRENT);
        when(localRepo.getCurrentWeather(city)).thenReturn(Single.just(item));
        weatherList.add(item);

        TestObserver<WeatherItem> observer = interactor.getSavedWeather(city).test();
        observer
                .assertNoErrors()
                .assertValueSequence(weatherList)
                .assertComplete();

        verify(localRepo).getForecast(city);
        verify(localRepo).getCurrentWeather(city);
        verifyNoMoreInteractions(localRepo);
        verifyZeroInteractions(remoteRepo);
    }

    @Test
    public void shouldReturnEmptyWeatherItem_whenNotHasSavedWeather() {
        when(localRepo.getForecast(city)).thenReturn(Observable.empty());
        when(localRepo.getCurrentWeather(city)).thenReturn(Single.just(new WeatherItem()));

        TestObserver<WeatherItem> observer = interactor.getSavedWeather(city).test();
        observer
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(new WeatherItem())
                .assertComplete();

        verify(localRepo).getForecast(city);
        verify(localRepo).getCurrentWeather(city);
        verifyNoMoreInteractions(localRepo);
        verifyZeroInteractions(remoteRepo);
    }

    @Test
    public void shouldReturnChanges_whenDatabaseChanges() {
        when(localRepo.observeWeather()).thenReturn(Flowable.just(new DatabaseChange.DatabaseUpdate<>()));

        TestSubscriber<DatabaseChange<WeatherItem>> subscriber = interactor.observeWeather().test();
        subscriber.assertValueCount(1)
                .assertNoErrors();

        verify(localRepo, VerificationModeFactory.only()).observeWeather();
        verifyZeroInteractions(remoteRepo);
    }

    @Test
    public void shouldNotReturnChanges_whenDatabaseNotChanges() {
        when(localRepo.observeWeather()).thenReturn(Flowable.empty());

        TestSubscriber<DatabaseChange<WeatherItem>> subscriber = interactor.observeWeather().test();
        subscriber.assertValueCount(0)
                .assertNoErrors();

        verify(localRepo, VerificationModeFactory.only()).observeWeather();
        verifyZeroInteractions(remoteRepo);
    }

    @Test
    public void shouldReturnMoreItems_whenPutForecastToDatabaseSuccessfull() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        for (int i = 0; i < 10; i++) weatherList.add(item);

        when(remoteRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(remoteRepo.getCurrentWeather(city)).thenReturn(Single.just(item));
        when(localRepo.putForecast(weatherList)).thenReturn(Observable.fromIterable(weatherList));
        when(localRepo.putCurrentWeather(item)).thenReturn(Single.just(item));

        TestObserver<WeatherItem> observer = interactor.updateWeather(city).test();
        observer.assertValueCount(20)
                .assertNoErrors();

        verify(remoteRepo).getForecast(city);
        verify(remoteRepo).getCurrentWeather(city);
        verify(localRepo).putForecast(weatherList);
        verify(localRepo).putCurrentWeather(item);
    }

    @Test
    public void shouldReturnLessItems_whenPutForecastToDatabaseFailed() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        for (int i = 0; i < 10; i++) weatherList.add(item);
        Throwable t = new Throwable();

        when(remoteRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(remoteRepo.getCurrentWeather(city)).thenReturn(Single.just(item));
        when(localRepo.putForecast(weatherList)).thenReturn(Observable.error(t));

        TestObserver<WeatherItem> observer = interactor.updateWeather(city).test();
        observer.assertError(t)
                .assertValueCount(9);

        verify(remoteRepo).getForecast(city);
        verify(remoteRepo).getCurrentWeather(city);
        verify(localRepo).putForecast(weatherList);
        verifyNoMoreInteractions(localRepo);
    }

    @Test
    public void shouldNotPutToDatabase_whenNotEnoughItemsFromRemote() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        for (int i = 0; i < 9; i++) weatherList.add(item);

        when(remoteRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(remoteRepo.getCurrentWeather(city)).thenReturn(Single.just(item));
        when(localRepo.putCurrentWeather(item)).thenReturn(Single.just(item));

        TestObserver<WeatherItem> observer = interactor.updateWeather(city).test();
        observer.assertValueCount(10)
                .assertNoErrors();

        verify(remoteRepo).getForecast(city);
        verify(remoteRepo).getCurrentWeather(city);
        verify(localRepo, never()).putForecast(weatherList);
        verify(localRepo).putCurrentWeather(item);
    }

    @Test
    public void shouldReturnLessItems_whenErrorFromRemoteForecast() {
        Throwable t = new Throwable();
        when(remoteRepo.getForecast(city)).thenReturn(Observable.error(t));
        when(remoteRepo.getCurrentWeather(city)).thenReturn(Single.just(new WeatherItem()));

        TestObserver<WeatherItem> observer = interactor.updateWeather(city).test();
        observer.assertError(t);

        verify(remoteRepo).getForecast(city);
        verify(remoteRepo).getCurrentWeather(city);
        verifyZeroInteractions(localRepo);
    }

    @Test
    public void shouldReturnSavedWeather_whenHasInDatabase() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        item.setCity(city.getId());
        for (int i = 0; i < 10; i++) weatherList.add(item);
        when(localRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(localRepo.getCurrentWeather(city)).thenReturn(Single.just(item));

        TestObserver<WeatherItem> observer = interactor.getWeather(city).test();
        weatherList.add(item);
        observer
                .assertValueCount(11)
                .assertValueSequence(weatherList)
                .assertNoErrors()
                .assertComplete();

        verify(localRepo).getForecast(city);
        verify(localRepo).getCurrentWeather(city);
        verifyZeroInteractions(remoteRepo);
    }

    @Test
    public void shouldUpdateWeather_whenNoForecastInDatabase() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        item.setCity(city.getId());
        item.setType(WeatherItem.WeatherTypes.FORECAST);
        for (int i = 0; i < 10; i++) weatherList.add(item);
        item = new WeatherItem();
        item.setCity(city.getId());
        item.setType(WeatherItem.WeatherTypes.CURRENT);
        when(localRepo.getForecast(city)).thenReturn(Observable.empty());
        when(localRepo.getCurrentWeather(city)).thenReturn(Single.just(item));
        when(remoteRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(remoteRepo.getCurrentWeather(city)).thenReturn(Single.just(item));

        TestObserver<WeatherItem> observer = interactor.getWeather(city).test();
        weatherList.add(item);
        observer
                .assertNoErrors()
                .assertComplete();

        verify(localRepo).getForecast(city);
        verify(localRepo).getCurrentWeather(city);
        verify(remoteRepo).getForecast(city);
        verify(remoteRepo).getCurrentWeather(city);
    }

    @Test
    public void shouldUpdateWeather_whenNoCurrentWeatherInDatabase() {
        List<WeatherItem> weatherList = new ArrayList<>();
        WeatherItem item = new WeatherItem();
        item.setCity(city.getId());
        item.setType(WeatherItem.WeatherTypes.FORECAST);
        for (int i = 0; i < 10; i++) weatherList.add(item);
        when(localRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(localRepo.getCurrentWeather(city)).thenReturn(Single.just(new WeatherItem()));
        when(remoteRepo.getForecast(city)).thenReturn(Observable.fromIterable(weatherList));
        when(remoteRepo.getCurrentWeather(city)).thenReturn(Single.just(new WeatherItem()));

        TestObserver<WeatherItem> observer = interactor.getWeather(city).test();
        weatherList.add(item);
        observer
                .assertNoErrors()
                .assertComplete();

        verify(localRepo).getForecast(city);
        verify(localRepo).getCurrentWeather(city);
        verify(remoteRepo).getForecast(city);
        verify(remoteRepo).getCurrentWeather(city);
    }
}
