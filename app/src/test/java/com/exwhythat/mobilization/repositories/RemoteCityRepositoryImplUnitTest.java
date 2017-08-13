package com.exwhythat.mobilization.repositories;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.part.Location;
import com.exwhythat.mobilization.network.CityApi;
import com.exwhythat.mobilization.network.cityResponse.CityResponse;
import com.exwhythat.mobilization.network.cityResponse.Geometry;
import com.exwhythat.mobilization.network.cityResponse.Result;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.network.suggestResponse.SuggestResponse;
import com.exwhythat.mobilization.repository.cityRepository.CityRepository;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

/**
 * Created by Grechka on 30.07.2017.
 */

public class RemoteCityRepositoryImplUnitTest {
    private final String CITY_NAME = "Moscow";
    private final double CITY_LATITUDE = 55.75222;
    private final double CITY_LONGITUDE = 37.615555;

    @Mock
    CityApi cityApi;

    private CityRepository repo;
    private City city = new City(CITY_NAME, CITY_LATITUDE, CITY_LONGITUDE);;
    private Prediction[] predictions = new Prediction[2];

    @Before
    public void onInit() {
        MockitoAnnotations.initMocks(this);
        repo = new RemoteCityRepositoryImpl(cityApi);
        predictions[0] = new Prediction("Moscow, Russia", "ChIJybDUc_xKtUYRTM9XV8zWRD0");
        predictions[1] = new Prediction("Moscow, ID, United States", "ChIJ0WHAIi0hoFQRbK3q5g0V_T4");
    }

    @Test
    public void shouldGetCorrectCitySuggest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(predictions[0]);
        predictionList.add(predictions[1]);
        SuggestResponse suggestions = new SuggestResponse(predictionList);

        when(cityApi.getCitySuggest(CITY_NAME, CityApi.CITY_PLACE_TYPES, CityApi.CITY_API_KEY_VALUE))
                .thenReturn(Observable.just(suggestions));

        TestObserver<Prediction> observer = repo.getCitySuggest(CITY_NAME).test();
        observer
                .assertNoErrors()
                .assertValueCount(2)
                .assertValues(predictions);
    }

    @Test
    public void shouldGetWrongCitySuggest() {
        when(cityApi.getCitySuggest(CITY_NAME, CityApi.CITY_PLACE_TYPES, CityApi.CITY_API_KEY_VALUE))
                .thenReturn(Observable.just(new SuggestResponse()));

        TestObserver<Prediction> observer = repo.getCitySuggest(CITY_NAME).test();
        observer
                .assertTerminated()
                .assertError(Exception.class);
    }

    @Test
    public void shouldGetCorrectCityInfo() {
        Geometry geometry = new Geometry(new Location(CITY_LATITUDE, CITY_LONGITUDE));
        Result result = new Result(CITY_NAME, geometry);
        CityResponse cityResponse = new CityResponse(result);

        when(cityApi.getCityInfo("id", CityApi.CITY_API_KEY_VALUE))
                .thenReturn(Single.just(cityResponse));

        TestObserver<City> observer = repo.getCityInfo("id").test();
        observer
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(city);
    }

    @Test
    public void shouldGetWrongCityInfo() {
        when(cityApi.getCityInfo("id", CityApi.CITY_API_KEY_VALUE))
                .thenReturn(Single.just(new CityResponse(null)));

        TestObserver<City> observer = repo.getCityInfo("id").test();
        observer
                .assertTerminated()
                .assertError(Exception.class);
    }
}