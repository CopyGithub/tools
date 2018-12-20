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
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
        out += String.format("Mac Address: %s\n", getLocalMacAddressFromIp());
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

    @SuppressLint("MissingPermission")
    private String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mSdk < 23) {
            return tm.getDeviceId();
        } else if (mSdk < 26) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "READ_PHONE_STATE";
            } else {
                return tm.getDeviceId();
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String did = tm.getImei();
                if (null == did) {
                    did = tm.getMeid();
                }
                return did;
            } else {
                return "READ_PHONE_STATE";
            }
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

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    private String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {
                NetworkInterface ni = en_netInterface.nextElement();
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    Log.e("******", ip.toString());
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }
                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
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