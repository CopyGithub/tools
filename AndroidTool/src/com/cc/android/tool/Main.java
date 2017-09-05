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
        String prefix = "    ";
        System.out.println(String.format("%s其中命令名根据批处理文件名或shell文件名来确定", prefix));
        System.out.println("选项包括:");
        System.out.println(String.format("%s%s [-r] apkpath\t安装apk,-r保留数据进行升级安装", prefix, INSTALL));
        System.out.println(String.format("%s%s [packagename]\t卸载apk,不带参数可以选择设备里可卸载的包", prefix, UNINSTALL));
        System.out.println(String.format("%s%s -dump|-xmltree apkpath\t解析apk的manifest文件", prefix, AAPT));
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