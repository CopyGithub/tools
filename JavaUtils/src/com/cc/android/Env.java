package com.cc.android;

import java.io.File;
import java.util.Map;

public class Env {
    public String adb = "";
    public String aapt = "";
    public String apkSigner = "";
    public String keytool = "";
    public String zipalign = "";

    public String javaHome = "";
    public String androidSDK = "";
    private String latestBuildTools = "";

    public Env() {
        setBaseEnv();
        if (!androidSDK.isEmpty()) {
            setLatestBuildTools();
            adb = androidSDK + "/platform-tools/adb.exe";
            aapt = latestBuildTools + "/aapt.exe";
            apkSigner = latestBuildTools + "/apksigner.bat";
            zipalign = latestBuildTools + "/zipalign.exe";
        }
        if (!javaHome.isEmpty()) {
            keytool = javaHome + "/bin/keytool.exe";
        }
    }

    private void setBaseEnv() {
        Map<String, String> map = System.getenv();
        if (map.containsKey("ANDROID_SDK_ROOT")) {
            androidSDK = map.get("ANDROID_SDK_ROOT");
        }
        if (map.containsKey("ANDROID_HOME")) {
            androidSDK = map.get("ANDROID_HOME");
        }
        if (map.containsKey("JAVA_HOME")) {
            javaHome = map.get("JAVA_HOME");
        }
    }

    private void setLatestBuildTools() {
        File buildTools = new File(androidSDK + "/build-tools");
        String[] path = new String[]{"0", "0", "0"};
        for (File file : buildTools.listFiles()) {
            String[] fileName = file.getName().split("\\.");
            for (int i = 0; i < fileName.length; i++) {
                if (Integer.valueOf(path[i]) < Integer.valueOf(fileName[i])) {
                    path = fileName;
                    break;
                }
            }
        }
        latestBuildTools = androidSDK + "/build-tools/" + path[0] + "." + path[1] + "." + path[2];
    }
}
