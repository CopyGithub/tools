package com.apitest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class FileOperation {

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
    public static boolean createFileOrDir(File file, boolean dir, boolean delete) {
        boolean flag = true;
        if (file.exists()) {
            if (delete || (dir != file.isDirectory())) {
                flag = deleteFileOrDir(file);
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
     * 删除指定路径下的所有文件,然后删除自己
     * 
     * @param filePath
     *            文件的绝对路径
     * @return {@code true} 所有文件都正常删除{@code false} 有一个文件删除报错都是{@code false}
     */
    public static boolean deleteFileOrDir(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        for (File child : file.listFiles()) {
            if (!deleteFileOrDir(child)) {
                return false;
            }
        }
        return file.delete();
    }

    public static boolean rename(File oldFile, String newName) {
        String oldName = oldFile.getName();
        if (newName != null) {
            if (oldFile.isFile() && oldName.indexOf(".") > 0) {
                String suffix = oldName.substring(oldName.lastIndexOf("."));
                newName = newName.endsWith(suffix) ? newName : (newName + suffix);
            }
            try {
                oldFile.renameTo(new File(oldFile.getParent() + File.separator + newName));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String readText(File file) {
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static void writeText(String text, File file, boolean append) {
        try {
            byte[] buffer = text.getBytes("UTF-8");
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
