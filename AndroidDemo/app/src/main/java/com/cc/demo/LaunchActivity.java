package com.cc.demo;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.tools.Screen;

public class LaunchActivity extends Activity {
    private ImageView mSplash;
    private ImageView mIcon;
    private TextView mAppName;
    private TextView mAppSlogan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mSplash = findViewById(R.id.splash);
        mIcon = findViewById(R.id.icon);
        mAppName = findViewById(R.id.appName);
        mAppSlogan = findViewById(R.id.appSlogan);
        initView();
    }

    private void initView() {
        Screen screen = new Screen(this);

        int[] splashLocation = new int[]{0, 0, 0, 0};
        int[] splashSize = new int[]{screen.metrics.widthPixels, screen.metrics.widthPixels * 4 / 3};
        setViewLocationAndSize(mSplash, splashLocation, splashSize);
        mSplash.setScaleType(ImageView.ScaleType.FIT_XY);

        float appNameWidth = mAppName.getPaint().measureText(mAppName.getText().toString());
        float appNameHeight = mAppName.getPaint().getFontSpacing();
        float appSloganWidth = mAppSlogan.getPaint().measureText(mAppSlogan.getText().toString());
        float appSloganHeight = mAppSlogan.getPaint().getFontSpacing();
        int maxWidth = (int) (appNameWidth > appSloganWidth ? appNameWidth : appSloganWidth);
        int iconX = (int) ((screen.metrics.widthPixels - (48 + 10) * screen.metrics.density - maxWidth) / 2);
        int iconY = (int) ((screen.metrics.heightPixels - screen.metrics.widthPixels * 4 / 3 - 48 * screen.metrics.density - screen.statusBarHeight) / 2);
        int appNameX = (int) ((maxWidth - appNameWidth) / 2 + 10 * screen.metrics.density);
        int appNameY = iconY;
        int appSloganX = (int) ((maxWidth - appSloganWidth) / 2 + 10 * screen.metrics.density);
        int appSloganY = (int) (48 * screen.metrics.density - appSloganHeight - appNameHeight);

        setViewLocationAndSize(mIcon, new int[]{iconX, iconY, 0, 0}, new int[]{(int) (48 * screen.metrics.density), (int) (48 * screen.metrics.density)});
        setViewLocationAndSize(mAppSlogan, new int[]{appSloganX, appSloganY, 0, 0}, new int[]{(int) appSloganWidth, (int) appSloganHeight});
        setViewLocationAndSize(mAppName, new int[]{appNameX, appNameY, 0, 0}, new int[]{(int) appNameWidth, (int) appNameHeight});
    }

    private void setViewLocationAndSize(View view, int[] location, int[] size) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(location[0], location[1], location[2], location[3]);
        LayoutParams params = new LayoutParams(margin);
        params.width = size[0];
        params.height = size[1];
        view.setLayoutParams(params);
    }
}
