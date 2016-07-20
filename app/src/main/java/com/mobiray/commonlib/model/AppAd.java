package com.mobiray.commonlib.model;

import android.provider.BaseColumns;

/**
 * Created by Alexey on 18.01.2016.
 */
public class AppAd implements BaseColumns {

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PACKAGE_ID = "package_id";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    public Long id;
    public String name;
    public String packageId;
    public String imagePath;

    public AppAd() {}

    public AppAd(String name, String packageId, String imagePath) {
        this.name = name;
        this.packageId = packageId;
        this.imagePath = imagePath;
    }
}
