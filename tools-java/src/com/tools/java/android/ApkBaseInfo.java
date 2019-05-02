package com.tools.java.android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tools.java.common.JavaCommon;

public class ApkBaseInfo {
    public String filePath = "";// 文件路径
    public long fileSize;// 文件大小
    // APK公有属性(dump)
    public String name = "";// 包名(默认)
    public int versionCode;// 版本号
    public String versionName = "";// 版本名
    public String installLocation = ""; // 首选安装位置
    public int sdkVersion;// 最低适配版本
    public int targetSdkVersion;// 目标版本
    public ArrayList<String> usesPermissions = new ArrayList<>();// 用户权限
    public Map<String, String> applicationLabel = new HashMap<>();// 程序名
    public boolean debuggable = false;// debug状态
    public String launchableActivity = "";// 启动Activity
    public ArrayList<String> usesFeature = new ArrayList<>();// 设备功能
    public String supportsScreens = "";// 支持的屏幕类型
    public boolean supportsAnyDensity = false;// 支持任意分辨率
    public String nativeCode = "";// 本地代码支持的CPU类型

    public ApkBaseInfo(String filePath, Env env) throws Exception {
        this.filePath = filePath;
        try {
            fileSize = new File(filePath).length();
        } catch (Exception e) {
            throw new Exception(String.format("Apk路径%s不存在", filePath));
        }
        aaptDump(env);
    }

    /**
     * 解析命令{@code aapt dump badging命令获取的信息}
     */
    private void aaptDump(Env env) throws Exception {
        ArrayList<String> out = new ArrayList<String>();
        String aapt = String.format("%s dump badging %s", env.aapt, filePath);
        JavaCommon javaCommon = new JavaCommon();
        javaCommon.runtimeExec(out, aapt, 30);
        Map<String, String> maps = new HashMap<>();
        for (String string : out) {
            if ("application-debuggable".equals(string.trim())) {
                debuggable = true;
            }
            Map<String, String> map = getMap(string);
            maps.putAll(map);
            if (map.containsKey("uses-permission.name")) {
                usesPermissions.add(map.get("uses-permission.name"));
            } else if (map.containsKey("uses-feature")) {
                usesFeature.add(map.get("uses-feature.name"));
            }
        }
        if (maps.containsKey("package.name")) {
            name = maps.get("package.name");
        }
        if (maps.containsKey("package.versionCode")) {
            versionCode = Integer.valueOf(maps.get("package.versionCode"));
        }
        if (maps.containsKey("package.versionName")) {
            versionName = maps.get("package.versionName");
        }
        if (maps.containsKey("install-location")) {
            installLocation = maps.get("install-location");
        }
        if (maps.containsKey("sdkVersion")) {
            sdkVersion = Integer.valueOf(maps.get("sdkVersion"));
        }
        if (maps.containsKey("targetSdkVersion")) {
            targetSdkVersion = Integer.valueOf(maps.get("targetSdkVersion"));
        }
        if (maps.containsKey("launchable-activity.name")) {
            launchableActivity = maps.get("launchable-activity.name");
        }
        if (maps.containsKey("supports-screens")) {
            supportsScreens = maps.get("supports-screens");
        }
        if (maps.containsKey("supports-any-density")) {
            String supportsAnyDensityString = maps.get("supports-any-density");
            supportsAnyDensity = "true".equalsIgnoreCase(supportsAnyDensityString) ? true : false;
        }
        if (maps.containsKey("native-code")) {
            nativeCode = maps.get("native-code");
        }
        setApplicationLabel(maps);
        if (name.isEmpty()) {
            throw new Exception("解析错误，请检查apk文件的路径");
        }
    }

    private Map<String, String> getMap(String string) {
        Map<String, String> maps = new HashMap<>();
        ArrayList<String> topArray = removeSpace(string.split(":"));
        if (topArray.size() > 1) {
            maps.put(topArray.get(0), topArray.get(1));
            ArrayList<String> secondArray = removeSpace(topArray.get(1).split(" "));
            for (String secondString : secondArray) {
                ArrayList<String> thirdArray = removeSpace(secondString.split("="));
                if (thirdArray.size() > 1) {
                    maps.put(topArray.get(0) + "." + thirdArray.get(0), thirdArray.get(1));
                }
            }
        }
        return maps;
    }

    private ArrayList<String> removeSpace(String[] array) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : array) {
            String trim = string.replace("'", "").trim();
            if (!trim.isEmpty()) {
                list.add(trim);
            }
        }
        return list;
    }

    private void setApplicationLabel(Map<String, String> maps) {
        if (maps.containsKey("locales")) {
            String string = maps.get("locales");
            String[] names = string.trim().split(" ");
            for (String name : names) {
                name = name.trim().replace("'", "");
                if ("--_--".equals(name)) {
                    name = "default";
                }
                String suffix = "default".equals(name) ? "" : "-" + name;
                applicationLabel.put(name, maps.get("application-label" + suffix));
            }
        }
    }

    public void toArray(ArrayList<String> out) {
        out.add(String.format("文件大小: %5.2fM", fileSize / 1024f / 1024f));
        out.add("包名: " + name);
        out.add("版本号: " + versionCode);
        out.add("版本名: " + versionName);
        out.add("安装位置: " + installLocation);
        out.add("最低适配版本: " + sdkVersion);
        out.add("目标版本: " + targetSdkVersion);
        out.add("用户权限如下: ");
        for (String string : usesPermissions) {
            out.add("    " + string);
        }
        out.add("程序名如下: ");
        for (String key : applicationLabel.keySet()) {
            out.add("    " + key + ": " + applicationLabel.get(key));
        }
        out.add("debug状态: " + debuggable);
        out.add("启动Activity: " + launchableActivity);
        out.add("设备功能列表如下: ");
        for (String string : usesFeature) {
            out.add("    " + string);
        }
        out.add("支持的屏幕类型: " + supportsScreens);
        out.add("支持任意分辨率: " + supportsAnyDensity);
        out.add("本地代码支持的CPU类型: " + nativeCode);
    }
}
