package com.mobiray.commonlib;

import android.app.Application;
import android.content.Context;

import com.mobiray.commonlib.servises.AdService;

/**
 * Created by Alexey on 18.01.2016.
 */
public class CommonApp extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AdService.startActionUpdateAdApps(this);
    }

    public static void setCustomContext(Context context) {
        instance = context;
    }

    public static Context getContext() {
        return instance;
    }
}
