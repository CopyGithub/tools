package com.cc.android.tool;

import com.cc.android.ApkBaseInfo;
import com.cc.android.Env;
import com.cc.common.JavaCommon;
import com.cc.io.ConsoleOperation;
import com.cc.io.FileOperation;

import java.io.File;
import java.util.ArrayList;

public class ApkManager {
    private ArrayList<String> mOut = new ArrayList<>();
    private String mCommand = "";
    private JavaCommon mJava = new JavaCommon();
    private Env mEnv = null;

    public ApkManager(Env env) throws Exception {
        this.mEnv = env;
        if ("".equals(mEnv.androidSDK)) {
            throw new Exception("缺少Android环境变量的配置");
        }
    }

    protected ArrayList<String> install(String[] args) throws Exception {
        if (args.length == 3) {
            if ("-r".equals(args[1])) {
                mCommand = "-r " + args[2];
            } else {
                throw new Exception("参数不正确，有过多参数，请核对");
            }
        } else if (args.length == 2) {
            mCommand = args[1];
        } else {
            throw new Exception("缺少apk路径或参数过多");
        }
        String device = Common.selectDevice(mEnv);
        mCommand = String.format("\"%s\"  -s %s install %s", mEnv.adb, device, mCommand);
        mJava.runtimeExec(mOut, mCommand, 30);
        return mOut;
    }

    protected ArrayList<String> uninstall(String[] args) throws Exception {
        String device = Common.selectDevice(mEnv);
        mCommand = String.format("\"%s\" -s %s uninstall ", mEnv.adb, device);
        if (args.length != 2) {
            throw new Exception("参数少于或多于2个，请查看帮助");
        }
        if ("-a".equalsIgnoreCase(args[1]) || "-3".equals(args[1])) {
            ArrayList<String> apps = getApps(device, "-a".equalsIgnoreCase(args[1]) ? "" : args[1]);
            if (apps.size() == 0) {
                throw new Exception("没有可以卸载的应用");
            }
            ArrayList<String> uninstallApps = ConsoleOperation.selectMultiInput(apps, true);
            if (uninstallApps.size() == 0) {
                throw new Exception("没有正确选择卸载的App");
            } else {
                int successNum = 0;
                int failedNum = 0;
                ArrayList<String> loopApps = uninstallApps.contains("all") ? apps : uninstallApps;
                for (String loopApp : loopApps) {
                    mOut.add(loopApp);
                    boolean success = mJava.runtimeExec(mOut, mCommand + loopApp, 30);
                    if (success && mOut.get(0).toLowerCase().contains("success")) {
                        successNum++;
                    } else {
                        failedNum++;
                    }
                }
                mOut.add(String.format("卸载成功%d个应用, 卸载失败%d个应用", successNum, failedNum));
            }
        } else {
            mCommand += args[1];
            mJava.runtimeExec(mOut, mCommand, 30);
        }
        return mOut;
    }

    protected ArrayList<String> aapt(String[] args) throws Exception {
        if (args.length == 3 && "-dump".equals(args[1])) {
            mCommand = String.format("\"%s\"  dump badging %s", mEnv.aapt, args[2]);
        } else if (args.length == 3 && "-xmltree".equals(args[1])) {
            mCommand = String.format("\"%s\"  d xmltree %s AndroidManifest.xml", mEnv.aapt, args[2]);
        } else {
            throw new Exception("参数不正确");
        }
        mJava.runtimeExec(mOut, mCommand, 30);
        return mOut;
    }

    protected ArrayList<String> apkCompare(String[] args) throws Exception {
        if (args.length == 3) {
            ApkBaseInfo firstApk = new ApkBaseInfo(args[1], mEnv);
            ApkBaseInfo secondApk = new ApkBaseInfo(args[2], mEnv);
            printCompareInfo(firstApk, secondApk);
            mOut.add("-------------------------------------");
            mOut.add("第一个包的信息如下: ");
            firstApk.toArray(mOut);
            mOut.add("-------------------------------------");
            mOut.add("第二个包的信息如下: ");
            secondApk.toArray(mOut);
        } else if (args.length == 2) {
            ApkBaseInfo apkBaseInfo = new ApkBaseInfo(args[1], mEnv);
            apkBaseInfo.toArray(mOut);
        } else {
            throw new Exception("缺少apk路径，参数需要1-2个apk路径，或者参数过多");
        }
        return mOut;
    }

    protected ArrayList<String> apkSign(String[] args) throws Exception {
        if (args.length == 4) {
            File apk = new File(args[3]);
            if (!(apk.exists() && apk.isFile())) {
                throw new Exception("apk路径不正确，请确认后重试");
            }
            File resignApk = new File(apk.getParentFile().getAbsolutePath() + "/resign.apk");
            File zipalignApk = new File(apk.getParentFile().getAbsolutePath() + "/zipalign.apk");
            FileOperation.fileDel(resignApk);
            FileOperation.fileDel(zipalignApk);
            mCommand = String.format("\"%s\"  sign --ks %s --ks-pass pass:%s --out %s %s", mEnv.apkSigner, args[1], args[2], resignApk.getAbsolutePath(), args[3]);
            mJava.runtimeExec(mOut, mCommand, 30);
            mCommand = String.format("\"%s\" 4 \"%s\" \"%s\"", mEnv.zipalign, resignApk.getAbsolutePath(), zipalignApk.getAbsolutePath());
            mJava.runtimeExec(mOut, mCommand, 30);
            FileOperation.fileDel(resignApk);
        } else {
            throw new Exception("缺少参数，请查看帮助信息");
        }
        return mOut;
    }

    private void printCompareInfo(ApkBaseInfo firstApk, ApkBaseInfo secondApk) {
        String difference = "%s不一致--第一个为:%s, 第二个为:%s.";
        String more = "第%d个多出来的%s为:";
        if (firstApk.fileSize != secondApk.fileSize) {
            mOut.add(String.format("文件大小不一样，第一个为:%5.2fM,第二个为:%5.2fM", firstApk.fileSize / 1024f / 1024f, secondApk.fileSize / 1024f / 1024f));
        }
        if (!firstApk.name.equals(secondApk.name)) {
            mOut.add(String.format(difference, "包名", firstApk.name, secondApk.name));
        }
        if (firstApk.versionCode != secondApk.versionCode) {
            mOut.add(String.format(difference, "版本号", firstApk.versionCode, secondApk.versionCode));
        }
        if (!firstApk.versionName.equals(secondApk.versionName)) {
            mOut.add(String.format(difference, "版本名", firstApk.versionName, secondApk.versionName));
        }
        if (!firstApk.installLocation.equals(secondApk.installLocation)) {
            mOut.add(String.format(difference, "首选安装位置", firstApk.installLocation, secondApk.installLocation));
        }
        if (firstApk.sdkVersion != secondApk.sdkVersion) {
            mOut.add(String.format(difference, "最低适配版本", firstApk.sdkVersion, secondApk.sdkVersion));
        }
        if (firstApk.targetSdkVersion != secondApk.targetSdkVersion) {
            mOut.add(String.format(difference, "目标版本", firstApk.targetSdkVersion, secondApk.targetSdkVersion));
        }
        if (!firstApk.usesPermissions.equals(secondApk.usesPermissions)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(firstApk.usesPermissions);
            temp.removeAll(secondApk.usesPermissions);
            if (temp.size() > 0) {
                mOut.add(String.format(more, 1, "权限"));
                for (String string : temp) {
                    mOut.add("    " + string);
                }
            }
            temp.clear();
            temp.addAll(secondApk.usesPermissions);
            temp.removeAll(firstApk.usesPermissions);
            if (temp.size() > 0) {
                mOut.add(String.format(more, 2, "权限"));
                for (String string : temp) {
                    mOut.add("    " + string);
                }
            }
        }
        if (!firstApk.applicationLabel.equals(secondApk.applicationLabel)) {
            mOut.add("程序名存在不一致的地方，具体如下");
            for (String key : firstApk.applicationLabel.keySet()) {
                if (secondApk.applicationLabel.containsKey(key)) {
                    String firstValue = firstApk.applicationLabel.get(key);
                    String secondValue = secondApk.applicationLabel.get(key);
                    if (!firstValue.equals(secondValue)) {
                        mOut.add("    " + String.format(difference, key, firstValue, secondValue));
                    }
                } else {
                    mOut.add("    " + String.format(more, 1, key) + firstApk.applicationLabel.get(key));
                }
            }
            for (String key : secondApk.applicationLabel.keySet()) {
                if (!firstApk.applicationLabel.containsKey(key)) {
                    mOut.add("    " + String.format(more, 2, key) + secondApk.applicationLabel.get(key));
                }
            }
        }
        if (firstApk.debuggable != secondApk.debuggable) {
            mOut.add(String.format(difference, "debug状态", firstApk.debuggable, secondApk.debuggable));
        }
        if (!firstApk.launchableActivity.equals(secondApk.launchableActivity)) {
            mOut.add(String.format(difference, "启动Activity", firstApk.launchableActivity, secondApk.launchableActivity));
        }
        if (!firstApk.usesFeature.equals(secondApk.usesFeature)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(firstApk.usesFeature);
            temp.removeAll(secondApk.usesFeature);
            if (temp.size() > 0) {
                mOut.add(String.format(more, 1, "硬件功能"));
                for (String string : temp) {
                    mOut.add("    " + string);
                }
            }
            temp.clear();
            temp.addAll(secondApk.usesFeature);
            temp.removeAll(firstApk.usesFeature);
            if (temp.size() > 0) {
                mOut.add(String.format(more, 2, "硬件功能"));
                for (String string : temp) {
                    mOut.add("    " + string);
                }
            }
        }
        if (!firstApk.supportsScreens.equals(secondApk.supportsScreens)) {
            mOut.add(String.format(difference, "支持的屏幕类型", firstApk.supportsScreens, secondApk.supportsScreens));
        }
        if (firstApk.supportsAnyDensity != secondApk.supportsAnyDensity) {
            mOut.add(String.format(difference, "支持所有density状态", firstApk.supportsAnyDensity, secondApk.supportsAnyDensity));
        }
        if (!firstApk.nativeCode.equals(secondApk.nativeCode)) {
            mOut.add(String.format(difference, "支持的cpu类型", firstApk.nativeCode, secondApk.nativeCode));
        }
    }

    private ArrayList<String> getApps(String device, String filter) {
        ArrayList<String> apps = new ArrayList<>();
        mJava.runtimeExec(apps, String.format("\"%s\"  -s %s shell pm list package %s", mEnv.adb, device, filter), 30);
        for (int i = 0; i < apps.size(); i++) {
            String[] app = apps.get(i).split(":");
            if (app.length > 1) {
                apps.set(i, app[1]);
            } else {
                apps.remove(i);
                i--;
            }
        }
        return apps;
    }
}
