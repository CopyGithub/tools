package com.cc.deviceinfos;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.FileOutputStream;

public class MainActivity extends Activity {
    Point mPoint = new Point();
    String mBrand = Build.BRAND;
    String mManufacturer = Build.MANUFACTURER;
    String mModel = Build.MODEL;
    String mBoard = Build.BOARD;
    String mRelease = Build.VERSION.RELEASE;
    int mSdk = Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        String out = getDevices();
        textView.setText(out);
        setContentView(textView);
        writeTxtToFile(out, Environment.getExternalStorageDirectory().getPath() + "/deviceInfo.txt");
    }

    private String getDevices() {
        String out = "";
        out += String.format("Brand: %s\n", mBrand);
        out += String.format("Manufacturer: %s\n", mManufacturer);
        out += String.format("Model: %s\n", mModel);
        out += String.format("Board: %s\n", mBoard);
        out += String.format("Release: %s\n", mRelease);
        out += String.format("SDK: %s\n", mSdk);
        out += String.format("Mac Address: %s\n", getMacAddress());
        out += String.format("Device ID:: %s\n", getDeviceId());
        out += String.format("Android ID: %s\n", Secure.getString(getContentResolver(), Secure.ANDROID_ID));
        out += String.format("Resolution: %s\n", getResolution());
        out += String.format("Screen Size: %5.2f inch\n", getScreenInches());
        out += String.format("Memory: %5.2fM\n", getMemory());
        return out;
    }

    private void writeTxtToFile(String out, String filePath) {
        try {
            FileOutputStream fOut = new FileOutputStream(filePath);
            byte[] bytes = out.getBytes();
            fOut.write(bytes);
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMacAddress() {
        String macAddress = "";
        if (mSdk < 23) {
            WifiManager wifiMng = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            macAddress = wifiMng.getConnectionInfo().getMacAddress();
        } else {
            //TODO
        }
        return macAddress;
    }

    private String getDeviceId() {
        if (mSdk < 26) {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } else {
            return "";
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private String getResolution() {
        WindowManager wm = ((WindowManager) getSystemService(Context.WINDOW_SERVICE));
        if (mSdk < 17) {
            wm.getDefaultDisplay().getSize(mPoint);
        } else {
            wm.getDefaultDisplay().getRealSize(mPoint);
        }
        return mPoint.y + "x" + mPoint.x;
    }

    private double getScreenInches() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(mPoint.x / dm.xdpi, 2);
        double y = Math.pow(mPoint.y / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }

    private float getMemory() {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memInfo);
        return (memInfo.totalMem) / (1024f * 1024f);
    }
}
