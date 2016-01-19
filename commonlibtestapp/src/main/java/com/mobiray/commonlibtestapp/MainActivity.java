package com.mobiray.commonlibtestapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiray.commonlib.database.CommonSQLite;
import com.mobiray.commonlib.model.AppAd;
import com.mobiray.commonlib.view.CommonActivity;

import java.io.FileNotFoundException;
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

        for (AppAd appAd : appAds) {
            stringBuilder.append(appAd.name).append("\n");

            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(openFileInput(appAd.imagePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
            root.addView(imageView, layoutParams);

        }
        textView.setText(stringBuilder.toString());
    }
}
