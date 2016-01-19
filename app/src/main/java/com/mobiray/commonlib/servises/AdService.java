package com.mobiray.commonlib.servises;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.mobiray.commonlib.database.CommonSQLite;
import com.mobiray.commonlib.model.AppAd;
import com.mobiray.commonlib.servises.events.EventMessage;
import com.mobiray.commonlib.servises.views.AppAdView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdService extends IntentService {

    private static final String ACTION_UPDATE_AD_APPS = "com.mobiray.commonlib.servises.action.UPDATE_AD_APPS";

    private MobirayService mobirayService;

    public static void startActionUpdateAdApps(Context context) {
        Intent intent = new Intent(context, AdService.class);
        intent.setAction(ACTION_UPDATE_AD_APPS);
        context.startService(intent);
    }

    public AdService() {
        super("AdService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MobirayService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mobirayService = retrofit.create(MobirayService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION_UPDATE_AD_APPS:
                    updateAdApps();
                    break;
            }
        }
    }

    private void updateAdApps() {
        CommonSQLite.isAppAdsUpdating = true;
        Call<List<AppAdView>> adInfoCall = mobirayService.getAdAppsList(getPackageName());
        List<AppAdView> adInfoList;
        try {
            adInfoList = adInfoCall.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            CommonSQLite.isAppAdsUpdating = false;
            return;
        }

        List<AppAd> appAds = downloadIcons(adInfoList);
        if (appAds.isEmpty()) {
            CommonSQLite.isAppAdsUpdating = false;
            return;
        }

        CommonSQLite.getInstance().insertOrUpdateAdApps(appAds);
        EventBus.getDefault().post(new EventMessage(EventMessage.CODE_AD_APPS_UPDATED));
    }

    private List<AppAd> downloadIcons(List<AppAdView> adInfoList) {
        List<AppAd> result = new ArrayList<>();

        Response<ResponseBody> response;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        for (AppAdView appAdView : adInfoList) {
            try {
                response = mobirayService.getImage(appAdView.imgUrl).execute();
                if (!response.isSuccess()) {
                    continue;
                }
                inputStream = response.body().byteStream();
                outputStream = openFileOutput(appAdView.imgUrl, MODE_PRIVATE);

                copy(inputStream, outputStream);

                result.add(new AppAd(appAdView.name, appAdView.packageId, appAdView.imgUrl));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                            outputStream = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
    }

}
