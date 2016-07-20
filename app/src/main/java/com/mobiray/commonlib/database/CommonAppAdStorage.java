package com.mobiray.commonlib.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiray.commonlib.model.AppAd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 18.01.2016.
 */
public class CommonAppAdStorage {

    private static final String KEY_AD_APPS = "KEY_AD_APPS";

    public static boolean isAppAdsUpdating = false;

    private static CommonAppAdStorage instance;

    private List<AppAd> appAds;

    public static CommonAppAdStorage getInstance() {
        if (instance == null) {
            instance = new CommonAppAdStorage();
        }
        return instance;
    }

    public List<AppAd> getAdApps(Context context) {
        if (appAds != null) {
            return appAds;
        }

        String json = getPrefs(context).getString(KEY_AD_APPS, null);
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        ArrayList<AppAd> appAds = gson.fromJson(json, new TypeToken<ArrayList<AppAd>>() {}.getType());
        this.appAds = appAds;
        return appAds;
    }

    public void insertOrUpdateAdApps(Context context, List<AppAd> appAds) {
        Gson gson = new Gson();
        String json = gson.toJson(appAds);
        getPrefs(context).edit().putString(KEY_AD_APPS, json).apply();
        isAppAdsUpdating = false;
        this.appAds = appAds;
    }

    public static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}