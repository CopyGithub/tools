package com.cc.android.tool;

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

    protected ArrayList<String> decode(String[] args) {
        ArrayList<String> out = new ArrayList<>();

        return out;
    }

    private String analyzeArgs(String[] args) {
        String command = args[0] + " ";
        if (args.length == 1) {
            command = "";
        } else if (args.length > 1) {
            if ("-r".equals(args[1])) {
                command = args.length > 2 ? command + "-r " + args[2] : "";
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
            apps.set(i, apps.get(i).split(":")[1]);
        }
        for (String app : KEEP_APPS) {
            apps.remove(app);
        }
        return apps;
    }
}
