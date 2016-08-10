package com.chchen.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {
    private final String[] exclusionList = { "com.android.chrome", "com.sohu.inputmethod.sogou",
            "com.tencent.mobileqq", "com.google.zxing.client.android" };

    // 执行exec命令
    public ArrayList<String> runtimeExec(String command) {
        ArrayList<String> al = new ArrayList<String>();
        try {
            Process proc = Runtime.getRuntime().exec("cmd /c " + command);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(proc.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                al.add(line);
            }
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return al;
    }

    @SuppressWarnings("unused")
    private boolean getPhoneVersion() {
        ArrayList<String> alSdk = runtimeExec("adb shell getprop ro.build.version.sdk");
        boolean sdkVersion = false;
        for (int i = 0; i < alSdk.size(); i++) {
            if (alSdk.get(i).contains("ro.build.version.sdk")) {
                int sdk = Integer.valueOf(alSdk.get(i).split("=")[1]);
                if (sdk >= 15) {
                    sdkVersion = true;
                }
            }
        }
        return sdkVersion;
    }

    /**
     * 获取需要卸载的应用列表并转为为卸载命令
     * 
     * @param al
     *            所有应用列表(pm list package -f)
     * @return
     */
    public ArrayList<String> getUninstallApp(ArrayList<String> al) {
        ArrayList<String> result = new ArrayList<>();
        String appPath = "";
        for (int i = 0; i < al.size(); i++) {
            appPath = al.get(i);
            if (appPath.contains("/system/app") || appPath.contains("/system/priv-app")
                    || appPath.contains("/system/framework") || appPath.equals("")) {
                continue;
            }
            boolean flag = false;
            for (String exclusion : exclusionList) {
                if (appPath.contains(exclusion)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println(appPath);
                result.add("adb uninstall " + appPath.split("=")[1]);
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    private String chooseDevice() {
        ArrayList<String> alDevice = runtimeExec("adb devices");
        ArrayList<String> alDevices = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < alDevice.size(); i++) {
            if (flag) {
                alDevices.add(alDevice.get(i));
            } else {
                if (alDevice.get(i).contains("List of devices attached")) {
                    flag = true;
                }
            }
        }
        for (int i = 0; i < alDevices.size(); i++) {
            System.out.println(i + 1 + "." + alDevices.get(i));
        }
        System.out.println("完");
        return null;
    }
}
