package com.cc.deviceinfos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

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
        requestPermissions();
    }

    private String getDevices() {
        String out = "";
        out += String.format("Brand: %s\n", mBrand);
        out += String.format("Manufacturer: %s\n", mManufacturer);
        out += String.format("Model: %s\n", mModel);
        out += String.format("Board: %s\n", mBoard);
        out += String.format("Release: %s\n", mRelease);
        out += String.format("SDK: %s\n", mSdk);
        out += String.format("Mac Address: %s\n", getMacByJavaAPI());
        out += String.format("Device ID: %s\n", getDeviceId());
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

    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    private String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mSdk < 23) {
            return tm.getDeviceId();
        } else if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            if (mSdk < 26) {
                return tm.getDeviceId();
            } else {
                String did = tm.getImei();
                if (null == did) {
                    did = tm.getMeid();
                }
                return did;
            }
        }
        return "";
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

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        requestPermissions(permissions, 123);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    System.exit(0);
                }
            }
        }
        TextView textView = new TextView(this);
        String out = getDevices();
        textView.setText(out);
        setContentView(textView);
        writeTxtToFile(out, Environment.getExternalStorageDirectory().getPath() + "/deviceInfo.txt");
    }
}