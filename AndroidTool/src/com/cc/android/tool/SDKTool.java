package com.cc.android.tool;

import com.cc.android.Env;
import com.cc.common.JavaCommon;
import com.cc.io.FileOperation;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SDKTool {
    private Env mEnv = null;
    private ArrayList<String> mOut = new ArrayList<>();
    private String mCommand = "";
    private JavaCommon mJava = new JavaCommon();

    public SDKTool(Env env) throws Exception {
        this.mEnv = env;
        if ("".equals(mEnv.androidSDK)) {
            throw new Exception("缺少Android环境变量的配置");
        }
    }

    protected ArrayList<String> startProguardgui() throws Exception {
        File proguardgui = new File(mEnv.proguardgui);
        File temp = new File(System.getProperty("java.class.path"));
        temp = new File(temp.getParent() + "/temp.bat");
        FileOperation.writeText("@echo off\r\n", temp, false, "utf-8", 1);
        FileOperation.writeText(String.format("cd /d \"%s\"", proguardgui.getParent()), temp, true, "utf-8", 1);
        FileOperation.writeText(String.format("\r\n%s", proguardgui.getName()), temp, true, "utf-8", 1);
        Desktop.getDesktop().open(temp);
        return mOut;
    }

    protected ArrayList<String> ndkStack(String[] args) throws Exception {
        if (args.length == 3) {
            mCommand = String.format("\"%s\" -sym \"%s\" -dump \"%s\"", mEnv.ndkStack, args[1], args[2]);
        } else {
            throw new Exception("缺少参数，请查看帮助信息");
        }
        mJava.runtimeExec(mOut, mCommand, 30);
        return mOut;
    }
}
