package com.mobiray.commonlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobiray.commonlib.CommonApp;
import com.mobiray.commonlib.model.AppAd;

import java.util.List;

import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Alexey on 18.01.2016.
 */
public class CommonSQLite extends SQLiteOpenHelper {

    public static boolean isAppAdsUpdating = false;

    private static final String DATABASE_NAME = "common.db";
    private static final int DATABASE_VERSION = 3;

    private static CommonSQLite instance;

    public static CommonSQLite getInstance() {
        if (instance == null) {
            instance = new CommonSQLite(CommonApp.getContext());
        }
        return instance;
    }

    static {
        CupboardFactory.setCupboard(new CupboardBuilder().useAnnotations().build());
        cupboard().register(AppAd.class);
    }

    public CommonSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }

    public List<AppAd> getAdApps() {
        SQLiteDatabase read = CommonSQLite.getInstance().getReadableDatabase();
        List<AppAd> result = cupboard().withDatabase(read).query(AppAd.class).list();
        read.close();
        return result;
    }

    public void insertOrUpdateAdApps(List<AppAd> appAds) {
        SQLiteDatabase writeDb = getWritableDatabase();
        SQLiteDatabase readDb = getReadableDatabase();

        cupboard().withDatabase(writeDb).delete(AppAd.class, "");

        AppAd existApp;
        for (AppAd appAd : appAds) {
            existApp = cupboard().withDatabase(readDb).query(AppAd.class)
                    .withSelection(AppAd.COLUMN_PACKAGE_ID + "=?", appAd.packageId).get();
            if (existApp != null) {
                appAd.id = existApp.id;
                cupboard().withDatabase(writeDb).update(AppAd.class,
                        cupboard().withEntity(AppAd.class).toContentValues(appAd));
            } else {
                cupboard().withDatabase(writeDb).put(appAd);
            }
        }
        writeDb.close();
        readDb.close();
        isAppAdsUpdating = false;
    }
}