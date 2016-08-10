package com.android.vending.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Utils {

    /**
     * 睡眠当前线程指定的{@code time} 毫秒数
     * 
     * @param time
     *            单位毫秒
     */
    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 执行本地命令,并将执行结果写入到{@link ArrayList}中
     * 
     * @param out
     *            存放执行结果,为{@code null} 时不记录
     * @param command执行的命令
     * @param timeout
     *            等待时间,单位为秒
     * @return 是否正常执行完成
     */
    public boolean runtimeExec(ArrayList<String> out, String command, int timeout) {
        ArrayList<String> backupOut = new ArrayList<String>();
        Process process = null;
        boolean result = false;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backupOut = out != null ? out : backupOut;
        new ReadInputStream(backupOut, process.getInputStream()).start();
        new ReadInputStream(backupOut, process.getErrorStream()).start();
        sleep(1000);// 增加多线程执行后的等待
        return result;
    }

    /**
     * 将输入流中的结果读取到{@link ArrayList} 中
     * 
     * @author chchen
     *
     */
    class ReadInputStream implements Runnable {
        private ArrayList<String> out;
        private InputStream inputStream;

        /**
         * 
         * @param out
         *            存放输入流的结果
         * @param inputStream
         *            输入流
         */
        public ReadInputStream(final ArrayList<String> out, InputStream inputStream) {
            this.out = out;
            this.inputStream = inputStream;
        }

        public void start() {
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }

        public void run() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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
     * 删除指定路径下的所有文件,然后删除自己
     * 
     * @param filePath
     *            文件的绝对路径
     * @return {@code true} 所有文件都正常删除{@code false} 有一个文件删除报错都是{@code false}
     */
    public boolean deleteFileOrDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        for (String fileName : file.list()) {
            if (!deleteFileOrDir(filePath + File.separator + fileName)) {
                return false;
            }
        }
        return file.delete();
    }

    /**
     * 创建文件或目录
     * 
     * @param filePath
     *            路径
     * @param dir
     *            是否为目录, {@code true} 表示创建目录, {@code false} 表示创建文件
     * @param delete
     *            是否删除已存在的目录
     * @return
     */
    public boolean createFileOrDir(String filePath, boolean dir, boolean delete) {
        File file = new File(filePath);
        boolean flag = true;
        if (file.exists()) {
            if (delete || (dir != file.isDirectory())) {
                flag = deleteFileOrDir(filePath);
                if (!flag) {
                    return false;
                }
            } else {
                return true;
            }
        }
        if (dir) {
            flag = file.mkdirs();
        } else {
            if (!file.getParentFile().exists()) {
                flag = file.getParentFile().mkdirs();
                if (!flag) {
                    return false;
                }
            }
            try {
                flag = file.createNewFile();
            } catch (IOException e) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 文件复制,支持复制目录以及目录下所有文件
     * 
     * @param fromPath
     *            来源文件夹或文件路径
     * @param toPath
     *            目标文件夹或文件路径. 注意:
     *            当目标文件或文件夹和来源文件和文件夹类型不一致时会被删除,如来源为文件,目标存在同名的文件夹,
     *            则文件夹会被删除,以便文件复制能够正常进行,反之亦然
     * @param delete
     *            {@code true} 删除目标位置的原有文件或文件夹{@code false} 表示覆盖目标位置原有的文件或文件夹
     * @return {@code true} 表示复制成功,{@code false} 表示任意一步失败
     */
    public boolean copyFileOrDir(String fromPath, String toPath, boolean delete) {
        File from = new File(fromPath);
        if (!from.exists()) {
            return false;
        }
        boolean dir = from.isDirectory();
        boolean flag = createFileOrDir(toPath, dir, delete);
        if (!flag) {
            return false;
        }
        if (!dir) {
            flag = deleteFileOrDir(toPath);
            if (!flag) {
                return false;
            }
            FileChannel in = null;
            FileChannel out = null;
            try {
                in = new FileInputStream(fromPath).getChannel();
                out = new FileOutputStream(toPath).getChannel();
                in.transferTo(0, in.size(), out);
                in.close();
                out.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        for (String fileName : from.list()) {
            flag = copyFileOrDir(fromPath + File.separator + fileName, toPath + File.separator
                    + fileName, false);
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    /**
     * 读取文件的{@link Buffer}
     * 
     * @param filePath
     *            文件路径
     * @return
     */
    private byte[] readFile(String filePath) {
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(filePath);
            buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            System.out.println("IO异常");
        }
        return buffer;
    }

    /**
     * 写入{@link Buffer}到文件中
     * 
     * @param buffer
     *            文件流
     * @param filePath
     *            文件路径
     * @param num
     *            重复写入流的次数,用于创建大文件
     * @param append
     *            是否追加,{@code true} 表示追加,[{@code false} 表示覆写
     */
    public void writeFile(byte[] buffer, String filePath, long num, boolean append) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, append);
            while (num-- > 0) {
                fos.write(buffer);
            }
            fos.close();
        } catch (IOException e) {
        }
    }

    /**
     * 读取文件中的内容并转化为String文本
     * 
     * @param filePath
     * @return 文件路径
     */
    public String readText(String filePath) {
        String text = "";
        try {
            text = new String(readFile(filePath), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("text:" + text);
        return text;
    }

    /**
     * 写入文本内容到文件中
     * 
     * @param text
     *            文本内容
     * @param filePath
     *            文件路径
     */
    public void writeText(String text, String filePath, boolean append) {
        byte[] buffer = null;
        try {
            buffer = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writeFile(buffer, filePath, 1, append);
    }
}
