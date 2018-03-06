package com.cc.android.tool;

import com.cc.android.Env;
import com.cc.io.ConsoleOperation;

import java.util.ArrayList;

public class Main {
    private static final String INSTALL = "install";
    private static final String UNINSTALL = "uninstall";
    private static final String AAPT = "aapt";
    private static final String COMPARE = "compare";
    private static final String APK_SIGN = "sign";
    private static final String JAVA_SIGN = "keytool";
    private static final String DEVICE_INFO = "deviceinfo";
    private static final String PROGUARD_GUI = "proguardgui";
    private static final String NDK_STACK = "ndkstack";

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                execArg(args, new Env());
            } catch (Exception e) {
                System.out.println("报错: " + e.getMessage());
            }
            return;
        }
        help();
    }

    private static void help() {
        System.out.println("用法：androidtool [-options] [args...]");
        String prefix = "    ";
        System.out.println(String.format("%s其中命令名根据批处理文件名来确定", prefix));
        System.out.println("选项包括:");
        System.out.println(String.format("%s%s [-r] apkpath\t\t安装apk,-r保留数据进行升级安装", prefix, INSTALL));
        System.out.println(String.format("%s%s [packagename/-3/-a]\t卸载apk,-3选择卸载第三方包，-a选择卸载所有包", prefix, UNINSTALL));
        System.out.println(String.format("%s%s -dump|-xmltree apkpath\t\t解析apk的manifest文件", prefix, AAPT));
        System.out.println(String.format("%s%s apkpath [apkpath]\t\t解析apk,或比较两个apk", prefix, COMPARE));
        System.out.println(String.format("%s%s keystore password apkpath\t使用指定的签名文件签名apk", prefix, APK_SIGN));
        System.out.println(String.format("%s%s <apkpath/alias time>\t查看签名或创建新签名", prefix, JAVA_SIGN));
        System.out.println(String.format("%s%s\t\t\t\t查看设备信息", prefix, DEVICE_INFO));
        System.out.println(String.format("%s%s\t\t\t\t启动proguardgui工具", prefix, PROGUARD_GUI));
        System.out.println(String.format("%s%s armeabi crashFile\t\t解析native的crash", prefix, NDK_STACK));
    }

    private static void execArg(String[] args, Env env) throws Exception {
        ArrayList<String> out = new ArrayList<>();
        if (INSTALL.equals(args[0])) {
            out = new ApkManager(env).install(args);
        } else if (UNINSTALL.equals(args[0])) {
            out = new ApkManager(env).uninstall(args);
        } else if (AAPT.equals(args[0])) {
            out = new ApkManager(env).aapt(args);
        } else if (COMPARE.equals(args[0])) {
            out = new ApkManager(env).apkCompare(args);
        } else if (APK_SIGN.equals(args[0])) {
            out = new ApkManager(env).apkSign(args);
        } else if (JAVA_SIGN.equals(args[0])) {
            out = new JavaTool(env).javaSign(args);
        } else if (DEVICE_INFO.equals(args[0])) {
            out = new DeviceTool(env).getDeviceInfo();
        } else if (PROGUARD_GUI.equals(args[0])) {
            out = new SDKTool(env).startProguardgui();
        } else if (NDK_STACK.equals(args[0])) {
            out = new SDKTool(env).ndkStack(args);
        }
        ConsoleOperation.printArrayString(out);
        ConsoleOperation.close();
    }
}