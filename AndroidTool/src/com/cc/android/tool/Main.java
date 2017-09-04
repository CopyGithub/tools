package com.cc.android.tool;

import com.cc.io.ConsoleOperation;

public class Main {
    private static final String INSTALL = "install";
    private static final String UNINSTALL = "uninstall";
    private static final String AAPT = "aapt";

    public static void main(String[] args) {
        if (args.length > 0) {
            if (execArg(args)) {
                return;
            } else {
                System.out.println("参数: " + args[0] + " 错误");
            }
        }
        help();
    }

    private static void help() {
        System.out.println("用法：androidtool [-options] [args...]");
        System.out.println("其中命令名根据批处理文件名或shell文件名来确定，选项包括:");
        String prefix = "    ";
        System.out.println(prefix + INSTALL + " apkpath\t\t安装apk");
        System.out.println(prefix + UNINSTALL + " [packagename]\t卸载apk,不带参数可以选择设备里可卸载的包");
    }

    private static boolean execArg(String[] args) {
        ApkManager apkManager = new ApkManager();
        if (INSTALL.equals(args[0])) {
            ConsoleOperation.printArrayString(apkManager.install(args));
        } else if (UNINSTALL.equals(args[0])) {
            ConsoleOperation.printArrayString(apkManager.uninstall(args));
        } else if (AAPT.equals(args[0])) {
            ConsoleOperation.printArrayString(apkManager.aapt(args));
        } else {
            return false;
        }
        ConsoleOperation.close();
        return true;
    }
}