package com.exwhythat.mobilization.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.model.WeatherItem;

import javax.inject.Inject;

import nl.qbusict.cupboard.Cupboard;

public class CupboardDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RxCupboard.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;
    private Cupboard cupboard;

    public synchronized SQLiteDatabase getConnection() {
        if (database == null) {
            database = getWritableDatabase();
        }
        return database;
    }

    @Inject
    public CupboardDbHelper(Context context, Cupboard cupboard) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cupboard = cupboard;
        cupboard.register(WeatherItem.class);
        cupboard.register(City.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard.withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard.withDatabase(db).upgradeTables();
    }

}