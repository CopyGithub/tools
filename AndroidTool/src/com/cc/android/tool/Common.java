package com.cc.android.tool;

import com.cc.android.Env;
import com.cc.common.JavaCommon;
import com.cc.io.ConsoleOperation;

import java.util.ArrayList;

public class Common {

    protected static String selectDevice(Env env) throws Exception {
        ArrayList<String> devices = new JavaCommon().getAllDevices(env);
        String device = "";
        if (devices.size() == 1) {
            device = devices.get(0);
        } else if (devices.size() > 1) {
            device = ConsoleOperation.selectInput(devices, false);
        } else {
            throw new Exception("没有可用的设备");
        }
        if ("".equals(device)) {
            throw new Exception("选择错误编号");
        }
        return device;
    }
}
