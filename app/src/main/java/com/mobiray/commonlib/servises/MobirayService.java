package com.mobiray.commonlib.servises;

import com.mobiray.commonlib.servises.views.AppAdView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by Alexey on 19.01.2016.
 */
public interface MobirayService {

    String BASE_URL = "http://mobiraysoftware.com";

    @GET("commonlib/ads.php")
    Call<List<AppAdView>> getAdAppsList(@Query("packageId") String packageId);

    @GET("commonlib/img/{imageName}")
    @Streaming
    Call<ResponseBody> getImage(@Path("imageName") String imageName);
}
