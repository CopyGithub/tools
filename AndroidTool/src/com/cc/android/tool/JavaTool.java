package com.cc.android.tool;

import com.cc.android.Env;
import com.cc.common.JavaCommon;
import com.cc.io.FileOperation;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class JavaTool {
    private ArrayList<String> mOut = new ArrayList<>();
    private String mCommand = "";
    private JavaCommon mJava = new JavaCommon();
    private Env mEnv = null;

    public JavaTool(Env env) throws Exception {
        this.mEnv = env;
        if (mEnv.javaHome.isEmpty()) {
            throw new Exception("缺少Java环境变量的配置");
        }
    }

    protected ArrayList<String> javaSign(String[] args) throws Exception {
        if (args.length == 2) {
            mCommand = String.format("%s -printcert -jarfile %s", mEnv.keytool, args[1]);
        } else if (args.length == 3) {
            mCommand = String.format("\"%s\" -genkey -v -alias %s -keyalg RSA -keysize 2048 -validity %s -keystore \"%s/desktop/%s.jks\"", mEnv.keytool, args[1], args[2], System.getProperty("user.home"), args[1]);
            File temp = new File(System.getProperty("java.class.path"));
            temp = new File(temp.getParent() + "/temp.bat");
            FileOperation.writeText(mCommand, temp, false, "utf-8", 1);
            FileOperation.writeText("\r\npause", temp, true, "utf-8", 1);
            Desktop.getDesktop().open(temp);
            return mOut;
        } else {
            throw new Exception("参数数目不正确");
        }
        mJava.runtimeExec(mOut, mCommand, 30, "gbk");
        return mOut;
    }
}
