package com.exwhythat.mobilization.network;

import com.exwhythat.mobilization.network.cityResponse.CityResponse;
import com.exwhythat.mobilization.network.suggestResponse.SuggestResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Grechka on 25.07.2017.
 */

public interface CityApi {
    String CITY_API_KEY_VALUE = "AIzaSyB6YnIYnkrKpFkKOA026xwtpK2KOqC260c";
    String CITY_PLACE_TYPES = "(cities)";

    @GET("autocomplete/json")
    Observable<SuggestResponse> getCitySuggest(@Query("input") String input,
                                               @Query("types") String types,
                                               @Query("key") String key);

    @GET("details/json")
    Single<CityResponse> getCityInfo(@Query("placeid") String placeId,
                                     @Query("key") String key);
}
