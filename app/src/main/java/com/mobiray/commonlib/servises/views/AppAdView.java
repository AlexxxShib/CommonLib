package com.mobiray.commonlib.servises.views;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alexey on 18.01.2016.
 */
public class AppAdView implements Serializable {

    @SerializedName("name")
    public String name;
    @SerializedName("package_id")
    public String packageId;
    @SerializedName("img_url")
    public String imgUrl;

}
