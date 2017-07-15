package com.exwhythat.mobilization.di.module;

import com.exwhythat.mobilization.BuildConfig;
import com.exwhythat.mobilization.network.ApiKeyInsertInterceptor;
import com.exwhythat.mobilization.network.RestApi;
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

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(loggingInterceptor);
        }

        return builder
                .addInterceptor(new ApiKeyInsertInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(WEATHER_API_END_POINT)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    RestApi provideRestApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }
}
