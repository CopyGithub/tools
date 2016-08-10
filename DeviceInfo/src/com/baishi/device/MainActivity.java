package com.baishi.device;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
    // private static final String FILE_PATH = "/mnt/sdcard/deviceInfo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        String out = getDevices();
        textView.setText(out);
        setContentView(textView);
        // writeTxtToFile(out, FILE_PATH);
    }

    private String getDevices() {
        String brand = android.os.Build.BRAND;
        String manufacturer = android.os.Build.MANUFACTURER;
        String model = android.os.Build.MODEL;
        String board = android.os.Build.BOARD;
        String release = android.os.Build.VERSION.RELEASE;
        WifiManager wifiMng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        String macAddress = wifiInfor.getMacAddress();
        int sdk = android.os.Build.VERSION.SDK_INT;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        String androidId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        Point point = new Point();
        WindowManager wm = ((WindowManager) getSystemService(Context.WINDOW_SERVICE));
        if (sdk < 13) {
            point.x = wm.getDefaultDisplay().getWidth();
            point.y = wm.getDefaultDisplay().getHeight();
        } else if (sdk < 17) {
            wm.getDefaultDisplay().getSize(point);
        } else {
            wm.getDefaultDisplay().getRealSize(point);
        }
        String resolution = point.y + "x" + point.x;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        MemoryInfo memInfo = new MemoryInfo();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memInfo);
        long memory = 0;
        if (sdk < 16) {
            // 此时memory输入0
        } else {
            memory = (memInfo.totalMem) / (1024 * 1024);
        }
        String out = String.format(
                "Brand: %s" + "\r\n" + "Manufacturer: %s" + "\r\n" + "Model: %s" + "\r\n"
                        + "Board: %s" + "\r\n" + "Release: %s" + "\r\n" + "SDK: %s" + "\r\n"
                        + "Device ID: %s" + "\r\n" + "Android ID: %s" + "\r\n" + "Resolution: %s"
                        + "\r\n" + "Screen Size: %s" + "\r\n" + "Mac Address: %s" + "\r\n"
                        + "Memory: %s",
                brand, manufacturer, model, board, release, sdk, deviceId, androidId, resolution,
                String.format("%.2f", screenInches), macAddress, memory);
        return out;
    }

    // 将字符串写入到文本文件中
    /*
     * private void writeTxtToFile(String strcontent, String filePath) { try {
     * FileOutputStream fout = new FileOutputStream(filePath); byte[] bytes =
     * strcontent.getBytes(); fout.write(bytes); fout.close(); } catch
     * (Exception e) { e.printStackTrace(); } }
     */
}
