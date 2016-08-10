package com.chchen;

import java.util.ArrayList;

import com.chchen.tools.Utils;

public class Main {

    public static void main(String[] args) {
        System.out.println("正在计算卸载项......");
        Utils utils = new Utils();
        ArrayList<String> alresult = utils.getUninstallApp(utils
                .runtimeExec("adb shell pm list package -f"));
        int num = 0; // 记录卸载成功的次数
        boolean flag = false;
        for (int i = 0; i < alresult.size(); i++) {
            flag = false;
            ArrayList<String> rs = utils.runtimeExec(alresult.get(i));
            for (int j = 0; j < rs.size(); j++) {
                if (rs.get(j).contains("Success")) {
                    System.out.println(alresult.get(i) + "  卸载成功");
                    flag = true;
                    num++;
                }
            }
            if (!flag) {
                System.out.println(alresult.get(i) + " 卸载失败");
            }
        }
        System.out.println("总计卸载软件: " + alresult.size() + "，其中卸载成功数为: " + num);
        System.out.println("卸载完成，按任意键退出......");
        // main.chooseDevice();
    }

}
