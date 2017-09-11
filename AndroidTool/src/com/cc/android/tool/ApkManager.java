package com.cc.android.tool;

import com.cc.android.ApkBaseInfo;
import com.cc.common.JavaCommon;
import com.cc.io.ConsoleOperation;

import java.util.ArrayList;

public class ApkManager {
    private static final String[] KEEP_APPS = {"com.android.chrome", "com.sohu.inputmethod.sogou",
            "com.tencent.mobileqq"};

    protected ArrayList<String> install(String[] args) {
        ArrayList<String> out = new ArrayList<>();
        String command = analyzeArgs(args);
        if (command.isEmpty()) {
            out.add("缺少apk路径");
            return out;
        }
        String device = getDevice();
        if (device.isEmpty()) {
            out.add("没有可用的设备");
            return out;
        }
        command = "adb -s " + device + " " + command;
        JavaCommon javaCommon = new JavaCommon();
        javaCommon.runtimeExec(out, command, 30);
        return out;
    }

    protected ArrayList<String> uninstall(String[] args) {
        ArrayList<String> out = new ArrayList<>();
        String device = getDevice();
        if (device.isEmpty()) {
            out.add("没有可用的设备");
            return out;
        }
        String command = analyzeArgs(args);
        JavaCommon javaCommon = new JavaCommon();
        if ("".equals(command)) {
            ArrayList<String> apps = getApps(device);
            if (apps.size() == 0) {
                out.add("没有可以卸载的应用");
                return out;
            }
            String app = ConsoleOperation.selectInput(apps, true);
            if ("all".equals(app)) {
                int num = 0;
                for (String loopApp : apps) {
                    command = "adb -s " + device + " uninstall " + loopApp;
                    out.add(loopApp);
                    boolean success = javaCommon.runtimeExec(out, command, 30);
                    num = success ? num++ : num;
                }
                out.add(String.format("成功卸载%d个应用", num));
                return out;
            } else {
                command = "uninstall " + app;
            }
        }
        command = "adb -s " + device + " " + command;
        javaCommon.runtimeExec(out, command, 30);
        return out;
    }

    protected ArrayList<String> aapt(String[] args) {
        ArrayList<String> out = new ArrayList<>();
        String command = analyzeArgs(args);
        if ("".equals(command)) {
            out.add("缺少apk路径参数");
            return out;
        }
        JavaCommon javaCommon = new JavaCommon();
        javaCommon.runtimeExec(out, command, 30);
        return out;
    }

    protected ArrayList<String> apkCompare(String[] args) {
        ArrayList<String> out = new ArrayList<>();
        if (args.length < 2) {
            out.add("缺少apk路径，参数需要1-2个apk路径");
            return out;
        }
        if (args.length > 2) {
            ApkBaseInfo firstApk = null;
            ApkBaseInfo secondApk = null;
            try {
                firstApk = new ApkBaseInfo(args[1]);
                secondApk = new ApkBaseInfo(args[2]);
            } catch (Exception e) {
                out.add(e.getMessage());
                return out;
            }
            printCompareInfo(firstApk, secondApk, out);
            out.add("-------------------------------------");
            out.add("第一个包的信息如下: ");
            firstApk.toArray(out);
            out.add("-------------------------------------");
            out.add("第二个包的信息如下: ");
            secondApk.toArray(out);
        } else {
            ApkBaseInfo apkBaseInfo = null;
            try {
                apkBaseInfo = new ApkBaseInfo(args[1]);
            } catch (Exception e) {
                out.add(e.getMessage());
                return out;
            }
            apkBaseInfo.toArray(out);
        }
        return out;
    }

    private void printCompareInfo(ApkBaseInfo firstApk, ApkBaseInfo secondApk, ArrayList<String> out) {
        String difference = "%s不一致--第一个为:%s, 第二个为:%s.";
        String more = "第%d个多出来的%s为:";
        if (firstApk.fileSize != secondApk.fileSize) {
            out.add(String.format("文件大小不一样，第一个为:%5.2fM,第二个为:%5.2fM", firstApk.fileSize / 1024f / 1024f, secondApk.fileSize / 1024f / 1024f));
        }
        if (!firstApk.name.equals(secondApk.name)) {
            out.add(String.format(difference, "包名", firstApk.name, secondApk.name));
        }
        if (firstApk.versionCode != secondApk.versionCode) {
            out.add(String.format(difference, "版本号", firstApk.versionCode, secondApk.versionCode));
        }
        if (!firstApk.versionName.equals(secondApk.versionName)) {
            out.add(String.format(difference, "版本名", firstApk.versionName, secondApk.versionName));
        }
        if (!firstApk.installLocation.equals(secondApk.installLocation)) {
            out.add(String.format(difference, "首选安装位置", firstApk.installLocation, secondApk.installLocation));
        }
        if (firstApk.sdkVersion != secondApk.sdkVersion) {
            out.add(String.format(difference, "最低适配版本", firstApk.sdkVersion, secondApk.sdkVersion));
        }
        if (firstApk.targetSdkVersion != secondApk.targetSdkVersion) {
            out.add(String.format(difference, "目标版本", firstApk.targetSdkVersion, secondApk.targetSdkVersion));
        }
        if (!firstApk.usesPermissions.equals(secondApk.usesPermissions)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(firstApk.usesPermissions);
            temp.removeAll(secondApk.usesPermissions);
            if (temp.size() > 0) {
                out.add(String.format(more, 1, "权限"));
                for (String string : temp) {
                    out.add("    " + string);
                }
            }
            temp.clear();
            temp.addAll(secondApk.usesPermissions);
            temp.removeAll(firstApk.usesPermissions);
            if (temp.size() > 0) {
                out.add(String.format(more, 2, "权限"));
                for (String string : temp) {
                    out.add("    " + string);
                }
            }
        }
//        if (!firstApk.applicationLabel.equals(secondApk.applicationLabel)) {
//            Map<String, String> temp = new HashMap<>();
//            temp.putAll(firstApk.applicationLabel);
//            temp.keySet().removeAll(secondApk.applicationLabel.keySet());
//            out.add(String.format(more, 1, "程序名", temp.toString()));
//            temp.clear();
//            temp.putAll(secondApk.applicationLabel);
//            temp.keySet().removeAll(firstApk.applicationLabel.keySet());
//            out.add(String.format(more, 2, "程序名", temp.toString()));
//        }
        if (firstApk.debuggable != secondApk.debuggable) {
            out.add(String.format(difference, "debug状态", firstApk.debuggable, secondApk.debuggable));
        }
        if (!firstApk.launchableActivity.equals(secondApk.launchableActivity)) {
            out.add(String.format(difference, "启动Activity", firstApk.launchableActivity, secondApk.launchableActivity));
        }
        if (!firstApk.usesFeature.equals(secondApk.usesFeature)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(firstApk.usesFeature);
            temp.removeAll(secondApk.usesFeature);
            if (temp.size() > 0) {
                out.add(String.format(more, 1, "硬件功能"));
                for (String string : temp) {
                    out.add("    " + string);
                }
            }
            temp.clear();
            temp.addAll(secondApk.usesFeature);
            temp.removeAll(firstApk.usesFeature);
            if (temp.size() > 0) {
                out.add(String.format(more, 2, "硬件功能"));
                for (String string : temp) {
                    out.add("    " + string);
                }
            }
        }
        if (!firstApk.supportsScreens.equals(secondApk.supportsScreens)) {
            out.add(String.format(difference, "支持的屏幕类型", firstApk.supportsScreens, secondApk.supportsScreens));
        }
        if (firstApk.supportsAnyDensity != secondApk.supportsAnyDensity) {
            out.add(String.format(difference, "支持所有density状态", firstApk.supportsAnyDensity, secondApk.supportsAnyDensity));
        }
        if (!firstApk.nativeCode.equals(secondApk.nativeCode)) {
            out.add(String.format(difference, "支持的cpu类型", firstApk.nativeCode, secondApk.nativeCode));
        }
    }


    private String analyzeArgs(String[] args) {
        String command = args[0] + " ";
        if (args.length == 1) {
            command = "";
        } else if (args.length > 1) {
            if ("-r".equals(args[1])) {
                command = args.length > 2 ? command + "-r " + args[2] : "";
            } else if ("-dump".equals(args[1])) {
                command = args.length > 2 ? command + "dump badging " + args[2] : "";
            } else if ("-xmltree".equals(args[1])) {
                command = args.length > 2 ? command + "d xmltree " + args[2] + " AndroidManifest.xml" : "";
            } else {
                command += args[1];
            }
        }
        return command;
    }

    private String getDevice() {
        JavaCommon javaCommon = new JavaCommon();
        ArrayList<String> devices = javaCommon.getAllDevices();
        String device = "";
        if (devices.size() == 1) {
            device = devices.get(0);
        } else if (devices.size() > 1) {
            device = ConsoleOperation.selectInput(devices, false);
        }
        return device;
    }

    private ArrayList<String> getApps(String device) {
        ArrayList<String> apps = new ArrayList<>();
        JavaCommon javaCommon = new JavaCommon();
        javaCommon.runtimeExec(apps, "adb -s " + device + " shell pm list package -3", 30);
        for (int i = 0; i < apps.size(); i++) {
            String[] app = apps.get(i).split(":");
            if (app.length > 1) {
                apps.set(i, app[1]);
            } else {
                apps.remove(i);
                i--;
            }
        }
        for (String app : KEEP_APPS) {
            apps.remove(app);
        }
        return apps;
    }
}
