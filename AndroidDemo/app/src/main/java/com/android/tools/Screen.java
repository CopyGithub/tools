package com.android.tools;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Screen {
    public DisplayMetrics metrics = new DisplayMetrics();
    public int xdp;
    public int ydp;
    public int statusBarHeight;

    public Screen(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        xdp = (int) (metrics.widthPixels / metrics.density + 0.5f);
        ydp = (int) (metrics.heightPixels / metrics.density + 0.5f);
        statusBarHeight = getStatusBarHeight(activity);
    }

    private int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        Resources resources = activity.getResources();
        //获取status_bar_height资源的ID
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
