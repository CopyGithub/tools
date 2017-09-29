package com.cc.android.tool;

import com.cc.android.Env;
import com.cc.io.FileOperation;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SDKTool {
    private Env mEnv = null;
    private ArrayList<String> mOut = new ArrayList<>();
    private String mCommand = "";

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
}
