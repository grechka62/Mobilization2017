package com.exwhythat.mobilization.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.exwhythat.mobilization.util.CupboardDbHelper;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.nl2312.rxcupboard2.RxCupboard;
import nl.nl2312.rxcupboard2.RxDatabase;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardBuilder;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Grechka on 04.08.2017.
 */

@Module
public class StorageModule {

    @Provides
    @NonNull
    @Singleton
    SharedPreferences provideDefaultPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @NonNull
    @Singleton
    SQLiteDatabase provideSQLiteDatabase(CupboardDbHelper helper) {
        return helper.getConnection();
    }

    @Provides
    @NonNull
    @Singleton
    CupboardDbHelper provideCupboardDbHelper(Context context, Cupboard cupboard) {
        return new CupboardDbHelper(context, cupboard);
    }

    @Provides
    @NonNull
    @Singleton
    Cupboard provideCupboard() {
        return new CupboardBuilder(cupboard()).useAnnotations().build();
    }

    @Provides
    @NonNull
    @Singleton
    RxDatabase provideRxDatabase(Cupboard cupboard, SQLiteDatabase database) {
        return RxCupboard.with(cupboard, database);
    }

    @Provides
    @NonNull
    @Singleton
    Calendar provideCalendar() {
        return Calendar.getInstance();
    }
}
