package com.cc.io;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleOperation {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * 从控制台获取一个{@code int}类型的值输入
     *
     * @return
     */
    public static int getInt() {
        return scanner.nextInt();
    }

    /**
     * 从控制台获取一个{@link String}类型的值输入
     *
     * @return
     */
    public static String getString() {
        return scanner.nextLine();
    }

    /**
     * 关闭一个{@link Scanner},需要所有{@link Scanner}实例都结束后使用
     */
    public static void close() {
        scanner.close();
    }

    /**
     * 打印文本列表中的内容到控制台
     *
     * @param contents
     */
    public static void printArrayString(ArrayList<String> contents) {
        for (String string : contents) {
            System.out.println(string);
        }
    }

    /**
     * 从列表中获取用户选择
     *
     * @param contents
     * @param all      添加所有选项
     * @return 返回选择的文本内容
     */
    public static String selectInput(ArrayList<String> contents, boolean all) {
        if (contents.size() == 0) {
            return "";
        }
        if (all) {
            System.out.println("0:all");
        }
        for (int i = 0; i < contents.size(); i++) {
            String content = contents.get(i);
            System.out.println((i + 1) + ":" + content);
        }
        System.out.print("输入选择的数字: ");
        int input = getInt();
        if (input > 0 && input <= contents.size()) {
            return contents.get(input - 1);
        } else if (input == 0) {
            return "all";
        } else {
            return "";
        }
    }
}
