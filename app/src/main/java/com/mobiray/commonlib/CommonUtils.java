package com.mobiray.commonlib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Alexey on 19.01.2016.
 */
public class CommonUtils {

    public static void goToMarket(String packageId, Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageId)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageId)));
        }
    }
}
