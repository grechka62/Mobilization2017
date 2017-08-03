package com.exwhythat.mobilization.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.exwhythat.mobilization.util.CupboardDbHelper;

import dagger.Module;
import dagger.Provides;
import nl.nl2312.rxcupboard2.RxCupboard;
import nl.nl2312.rxcupboard2.RxDatabase;

/**
 * Created by Grechka on 04.08.2017.
 */

@Module
public class StorageModule {

    @Provides
    @NonNull
    SharedPreferences provideDefaultPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
