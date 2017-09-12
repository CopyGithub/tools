package com.cc.common;

import com.cc.android.Env;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JavaCommon {

    /**
     * 睡眠当前线程指定的{@code time} 毫秒数
     *
     * @param time 单位毫秒
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 将{@link String}数组转化为一个{@link ArrayList}
     *
     * @param strings
     * @return
     */
    public static ArrayList<String> toArrayList(String[] strings) {
        ArrayList<String> arrays = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            arrays.add(strings[i]);
        }
        return arrays;
    }

    /**
     * 移除数组中指定的元素
     *
     * @param origin 原始数组
     * @param index  需要移除的元素索引
     * @return
     */
    public static int[] removeElement(int[] origin, int index) {
        int[] result = new int[origin.length - 1];
        int flag = 0;
        for (int i = 0; i < result.length; i++) {
            if (i == index) {
                flag = 1;
            }
            result[i] = origin[i + flag];
        }
        return result;
    }

    /**
     * 计算字符串的MD5值
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = string.getBytes();
        digester.update(bytes);
        StringBuffer des = new StringBuffer();
        String tmp = null;
        byte[] byteDigest = digester.digest();
        for (int i = 0; i < byteDigest.length; i++) {
            tmp = (Integer.toHexString(byteDigest[i] & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }

    /**
     * 将输入流中的结果读取到{@link ArrayList} 中
     *
     * @author chchen
     */
    class ReadInputStream implements Runnable {
        private ArrayList<String> out;
        private InputStream inputStream;
        private String charsetName;

        /**
         * @param out         存放输入流的结果
         * @param inputStream 输入流
         */
        public ReadInputStream(final ArrayList<String> out, InputStream inputStream, String charsetName) {
            this.out = out;
            this.inputStream = inputStream;
            this.charsetName = charsetName;
        }

        public void start() {
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }

        public void run() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
                String line;
                while ((line = reader.readLine()) != null) {
                    out.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行本地命令,并将执行结果写入到{@link ArrayList}中
     *
     * @param out     存放执行结果,为{@code null} 时不记录
     * @param command 执行的命令
     * @param timeout 等待时间,单位为秒
     * @return 是否正常执行完成
     */
    public boolean runtimeExec(ArrayList<String> out, String command, int timeout, String charsetName) {
        ArrayList<String> backupOut = new ArrayList<String>();
        Process process = null;
        boolean result = false;
        try {
            process = Runtime.getRuntime().exec(command);
            backupOut = out != null ? out : backupOut;
            new ReadInputStream(backupOut, process.getInputStream(), charsetName).start();
            new ReadInputStream(backupOut, process.getErrorStream(), charsetName).start();
            result = process.waitFor(timeout, TimeUnit.SECONDS);
            sleep(1000);// 增加多线程执行后的等待
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return result;
    }

    public boolean runtimeExec(ArrayList<String> out, String command, int timeout) {
        return runtimeExec(out, command, timeout, "utf-8");
    }

    /**
     * 获取所有在线设备序列号列表
     *
     * @return
     */
    public ArrayList<String> getAllDevices(Env env) {
        String cmd = env.adb + " devices";
        ArrayList<String> result = new ArrayList<>();
        runtimeExec(result, cmd, 10, "utf-8");
        ArrayList<String> devices = new ArrayList<String>();
        for (String string : result) {
            String[] device = string.trim().split("\\t");
            if (device.length == 2 && device[1].equals("device")) {
                devices.add(device[0].trim());
            }
        }
        return devices;
    }

    /**
     * 获取Android系统版本
     *
     * @return
     */
    private int getPhoneVersion(Env env, String device) {
        int sdk = 0;
        ArrayList<String> out = new ArrayList<>();
        String command = String.format("%s -s %s shell getprop ro.build.version.sdk", env.adb, device);
        runtimeExec(out, command, 30, "utf-8");
        for (String string : out) {
            if (string.contains("ro.build.version.sdk")) {
                sdk = Integer.valueOf(string.split("=")[1]);
            }
        }
        return sdk;
    }

    /**
     * 替换{@link JSONObject}中被{@code [[]]}括起来的文本，如果没有则原样返回
     *
     * @param script
     * @param config
     * @return
     * @throws JSONException
     * @throws UnsupportedEncodingException
     */
    public static JSONObject replaceScriptParam(JSONObject script, JSONObject config)
            throws JSONException, UnsupportedEncodingException {
        String value = script.toString();
        int start = value.indexOf("[[");
        int end = value.indexOf("]]");
        if (start != -1 && end != -1) {
            if (config != null && config.length() > 0) {
                String key = value.substring(start + 2, end).trim();
                String replace = URLEncoder.encode(config.getString(key).trim(), "utf-8");
                value = value.substring(0, start) + replace + value.substring(end + 2).trim();
                script = new JSONObject(value);
                script = replaceScriptParam(script, config);
            } else {
                throw new JSONException("没有配置文件");
            }
        }
        return script;
    }
}
