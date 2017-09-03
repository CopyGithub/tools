package com.cc.android.tool;

import com.cc.io.ConsoleOperation;

public class Main {
    private static final String INSTALL = "install";
    private static final String UNINSTALL = "uninstall";
    private static final String DECODE = "decode";

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
        System.out.println("其中选项包括:");
        String prefix = "    ";
        System.out.println(prefix + INSTALL + " apkpath\t安装apk");
    }

    private static boolean execArg(String[] args) {
        if (INSTALL.equals(args[0])) {
            ConsoleOperation.printArrayString(ApkManager.install(args));
        } else if (UNINSTALL.equals(args[0])) {
            ConsoleOperation.printArrayString(ApkManager.uninstall(args));
        } else {
            return false;
        }
        return true;
    }
}