package com.cc.android.tool;

import com.cc.common.JavaCommon;
import com.cc.io.ConsoleOperation;

import java.util.ArrayList;

public class ApkManager {
    private static final String[] KEEP_APPS = {"com.android.chrome", "com.sohu.inputmethod.sogou",
            "com.tencent.mobileqq"};

    protected static ArrayList<String> install(String[] args) {
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

    protected static ArrayList<String> uninstall(String[] args) {
        ArrayList<String> out = new ArrayList<>();
        String device = getDevice();
        if (device.isEmpty()) {
            out.add("没有可用的设备");
            return out;
        }
        String command = analyzeArgs(args);
        JavaCommon javaCommon = new JavaCommon();
        if (command.isEmpty()) {
            ArrayList<String> apps = getApps(device);
            String app = ConsoleOperation.selectInput(apps, true);
            if ("all".equals(app)) {
                int num = 0;
                for (String loopApp : apps) {
                    command = "adb -s " + device + " uninstall " + loopApp;
                    out.add(command);
                    boolean success = javaCommon.runtimeExec(out, command, 30);
                    if (success) {
                        num++;
                    }
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

    private static String analyzeArgs(String[] args) {
        String command = args[0] + " ";
        if (args.length == 1) {
            command = "";
        } else if (args.length > 1) {
            if ("-r".equals(args[1])) {
                if (args.length > 2) {
                    command = command + "-r " + args[2];
                } else {
                    command = "";
                }
            } else {
                command += args[1];
            }
        }
        return command;
    }

    private static String getDevice() {
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

    private static ArrayList<String> getApps(String device) {
        ArrayList<String> apps = new ArrayList<>();
        JavaCommon javaCommon = new JavaCommon();
        ArrayList<String> out = new ArrayList<>();
        javaCommon.runtimeExec(out, "adb -s " + device + " shell pm list -f", 30);
        for (String appPath : out) {
            if (appPath.contains("/system/app") || appPath.contains("/system/priv-app")
                    || appPath.contains("/system/framework") || appPath.equals("")) {
                continue;
            }
            boolean isKeepApp = false;
            for (String keepApp : KEEP_APPS) {
                if (appPath.contains(keepApp)) {
                    isKeepApp = true;
                    break;
                }
            }
            if (!isKeepApp) {
                apps.add(appPath.split("=")[1]);
            }
        }
        return apps;
    }
}
