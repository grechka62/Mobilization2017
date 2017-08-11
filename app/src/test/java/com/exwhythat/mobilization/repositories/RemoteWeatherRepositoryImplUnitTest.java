package com.exwhythat.mobilization.repositories;

import android.content.Context;

import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.network.cityResponse.part.Location;
import com.exwhythat.mobilization.network.weatherResponse.WeatherResponse;
import com.exwhythat.mobilization.network.weatherResponse.part.Main;
import com.exwhythat.mobilization.network.weatherResponse.part.Weather;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
import com.exwhythat.mobilization.util.Constants;
import com.exwhythat.mobilization.util.DataPrefs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

/**
 * Created by Grechka on 30.07.2017.
 */

public class RemoteWeatherRepositoryUnitTest {
    private final double CITY_LATITUDE = 55.75222;
    private final double CITY_LONGITUDE = 37.615555;

    @Mock
    WeatherApi weatherApi;
    @Mock
    Context context;
    @Mock
    DataPrefs prefs;

    private WeatherRepository repo;

    @Before
    public void onInit() {
        MockitoAnnotations.initMocks(this);
        repo = new RemoteWeatherRepository(weatherApi, context);
    }

    @Test
    public void shouldGetCorrectCurrentWeather() {
        List<Weather> weatherList = new ArrayList<>();
        Weather weather = new Weather("main", "description");
        weatherList.add(weather);
        WeatherResponse weatherResponse = new WeatherResponse(new Main(30.0), weatherList, 30);

        when(weatherApi.getWeatherForCity(CITY_LATITUDE, CITY_LONGITUDE,
                Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE))
                .thenReturn(Single.just(weatherResponse));

        TestObserver<WeatherResponse> observer =
                repo.getCurrentWeather(new Location(CITY_LATITUDE, CITY_LONGITUDE)).test();
        observer
                .assertError(NullPointerException.class)
                .assertTerminated();
    }

    @Test
    public void shouldGetWrongCurrentWeather() {
        when(weatherApi.getWeatherForCity(CITY_LATITUDE, CITY_LONGITUDE,
                Constants.Units.METRIC, WeatherApi.WEATHER_API_KEY_VALUE))
                .thenReturn(Single.just(new WeatherResponse(null, null, 0)));

        TestObserver<WeatherResponse> observer =
                repo.getCurrentWeather(new Location(CITY_LATITUDE, CITY_LONGITUDE)).test();
        observer
                .assertTerminated()
                .assertError(Exception.class);
    }
}
