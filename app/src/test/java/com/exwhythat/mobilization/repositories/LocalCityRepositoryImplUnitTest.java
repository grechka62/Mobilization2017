package com.exwhythat.mobilization.repositories;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Flowable;
import io.reactivex.observers.TestObserver;
import nl.nl2312.rxcupboard2.RxDatabase;

import static org.mockito.Mockito.when;

/**
 * Created by Grechka on 30.07.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class LocalCityRepositoryImplUnitTest {

    @Mock
    RxDatabase database;

    private LocalCityRepository repo;
    private City city;

    @Before
    public void onInit() {
        MockitoAnnotations.initMocks(this);
        repo = new LocalCityRepositoryImpl(database);

        city = new City("Moscow", 55.75222, 37.615555);
        when(database.query(City.class)).thenReturn(Flowable.just(city));
    }

    @Test
    public void shouldGetCityInfo() {
        TestObserver<City> observer = repo.getCityInfo("").test();
        observer
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(city);
    }
}