package com.exwhythat.mobilization.repositories;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.weatherRepository.LocalWeatherRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Single;
import nl.nl2312.rxcupboard2.RxDatabase;
import utils.CaptorUtils;
import utils.Yanswers;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Grechka on 10.08.2017.
 */

public class LocalWeatherRepositoryUnitTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
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

        when(database.delete(eq(WeatherItem.class), anyString(), any())).thenReturn(Single.just(0L));

        repository.putWeatherList(singletonList(item));

        ArgumentCaptor<String> captor = CaptorUtils.captor(String.class);
        verify(database).delete(eq(WeatherItem.class), anyString(), captor.capture());

        assertThat(captor.getAllValues()).contains(cityId + "");
    }

    @Test
    public void throws_whenCitiesHaveNotSameId() {
        try {
            repository.putWeatherList(new ArrayList<WeatherItem>() {{
                add(new WeatherItem(){{setCity(42);}});
                add(new WeatherItem(){{setCity(43);}});
            }});
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Should throw IllegalArgumentException if not all items has same city id!");
    }
}
