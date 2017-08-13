package com.exwhythat.mobilization.di.module;

import android.support.annotation.NonNull;

import com.exwhythat.mobilization.BuildConfig;
import com.exwhythat.mobilization.network.CityApi;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.repository.cityRepository.CityRepository;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepositoryImpl;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepositoryImpl;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by exwhythat on 15.07.17.
 */

@Module
public class NetworkModule {

    private static final String WEATHER_API_END_POINT = "http://api.openweathermap.org/data/2.5/";
    private static final String CITY_API_END_POINT = "https://maps.googleapis.com/maps/api/place/";

    @Provides
    @NonNull
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @NonNull
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    @Provides
    @NonNull
    @Singleton
    WeatherApi provideWeatherApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEATHER_API_END_POINT)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @NonNull
    @Singleton
    CityApi provideCityApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CITY_API_END_POINT)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(CityApi.class);
    }

    @Provides
    @NonNull
    @Singleton
    WeatherRepository provideRemoteWeatherRepository(WeatherApi weatherApi) {
        return new RemoteWeatherRepositoryImpl(weatherApi);
    }

    @Provides
    @NonNull
    @Singleton
    CityRepository provideRemoteCityRepository(CityApi cityApi) {
        return new RemoteCityRepositoryImpl(cityApi);
    }
}
