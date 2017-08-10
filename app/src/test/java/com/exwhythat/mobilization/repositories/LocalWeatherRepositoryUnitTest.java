package com.exwhythat.mobilization.repositories;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import nl.nl2312.rxcupboard2.RxDatabase;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;

/**
 * Created by Grechka on 10.08.2017.
 */

public class LocalWeatherRepositoryUnitTest {

    @Mock
    RxDatabase database;

    private LocalWeatherRepository repository;

    @Before
    public void onInit() {
        MockitoAnnotations.initMocks(this);
        Calendar calendar = Calendar.getInstance();
        repository = new LocalWeatherRepository(database, calendar);
    }

    @Test
    public void shouldClearForecast_whenPutNewForecast() {
        WeatherItem item = new WeatherItem();
        Long cityId = 1L;
        item.setCity(cityId);

        repository.putWeatherList(singletonList(item));
        verify(database.delete(WeatherItem.class, "city_id=? and type=2", Long.toString(cityId)));
    }
}
