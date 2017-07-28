package com.exwhythat.mobilization.di.module;

import android.content.Context;

import com.exwhythat.mobilization.BuildConfig;
import com.exwhythat.mobilization.di.ActivityContext;
import com.exwhythat.mobilization.network.WeatherApi;
import com.exwhythat.mobilization.network.CityApi;
import com.exwhythat.mobilization.repository.cityRepository.RemoteCityRepository;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import com.exwhythat.mobilization.repository.weatherRepository.RemoteWeatherRepository;
import com.exwhythat.mobilization.repository.weatherRepository.WeatherRepository;
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
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(loggingInterceptor);
        }
        return builder//.addInterceptor(new WeatherApiKeyInsertInterceptor())
                .build();
    }

    /*@Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(WEATHER_API_END_POINT)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }*/

    @Provides
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
    @Singleton
    WeatherRepository provideRemoteWeatherRepository(WeatherApi weatherApi, @ActivityContext Context context) {
        return new RemoteWeatherRepository(weatherApi, context);
    }

    @Provides
    @Singleton
    RemoteCityRepository provideRemoteCityRepository(CityApi cityApi) {
        return new RemoteCityRepository(cityApi);
    }
}
