package com.cc.android.tool;

import com.cc.android.Env;
import com.cc.common.JavaCommon;

import java.io.File;
import java.util.ArrayList;

public class DeviceTool {
    private Env mEnv = null;
    private ArrayList<String> mOut = new ArrayList<>();
    private String mCommand = "";
    private JavaCommon mJava = new JavaCommon();

    public DeviceTool(Env env) throws Exception {
        this.mEnv = env;
        if ("".equals(mEnv.androidSDK)) {
            throw new Exception("缺少Android环境变量的配置");
        }
    }

    protected ArrayList<String> getDeviceInfo() throws Exception {
        File deviceInfoApk = new File(System.getProperty("java.class.path"));
        deviceInfoApk = new File(deviceInfoApk.getParent() + "/deviceInfo.apk");
        String device = Common.selectDevice(mEnv);
        mCommand = String.format("\"%s\"  -s %s uninstall %s", mEnv.adb, device, "com.cc.deviceinfos");
        mJava.runtimeExec(mOut, mCommand, 30);
        mCommand = String.format("\"%s\"  -s %s install \"%s\"", mEnv.adb, device, deviceInfoApk.getAbsolutePath());
        mJava.runtimeExec(mOut, mCommand, 30);
        mCommand = String.format("\"%s\"  -s %s shell am start -W -n com.cc.deviceinfos/.MainActivity", mEnv.adb, device);
        mJava.runtimeExec(mOut, mCommand, 30);
        mOut.clear();
        mCommand = String.format("\"%s\"  -s %s shell cat /sdcard/deviceInfo.txt", mEnv.adb, device);
        mJava.runtimeExec(mOut, mCommand, 30);
        return mOut;
    }
}
