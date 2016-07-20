package com.mobiray.commonlibtestapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiray.commonlib.database.CommonSQLite;
import com.mobiray.commonlib.model.AppAd;
import com.mobiray.commonlib.view.CommonActivity;

import java.io.File;
import java.util.List;

public class MainActivity extends CommonActivity {

    LinearLayout root;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = (LinearLayout) findViewById(R.id.root);
        textView = (TextView) findViewById(R.id.text);
        showResults(CommonSQLite.getInstance().getAdApps());
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void adAppsUpdated() {
        showResults(CommonSQLite.getInstance().getAdApps());
    }

    private void showResults(List<AppAd> appAds) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        StringBuilder stringBuilder = new StringBuilder();

        Log.i("TAG_MainActivity", "" + appAds.size());
        for (AppAd appAd : appAds) {
            stringBuilder.append(appAd.name).append("\n");

            File file = getFileStreamPath(appAd.imagePath);
            Uri uri = Uri.fromFile(file);
//            Bitmap bitmap = null;
//            try {
//                bitmap = BitmapFactory.decodeStream(openFileInput(appAd.imagePath));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            ImageView imageView = new ImageView(this);
//            imageView.setImageBitmap(bitmap);
            imageView.setImageURI(uri);
            root.addView(imageView, layoutParams);

        }
        textView.setText(stringBuilder.toString());
    }
}
