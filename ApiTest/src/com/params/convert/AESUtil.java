package com.params.convert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by MHL on 2016/6/30.
 */
public class AESUtil {
    private static byte[] cipher(byte[] sSrc, String sKey, int cipherMode) throws Exception {
        if (sKey == null || sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(cipherMode, skeySpec, iv);
        return cipher.doFinal(sSrc);
    }

    public static byte[] decrypt(byte[] sSrc, String sKey) throws Exception {
        return cipher(sSrc, sKey, Cipher.DECRYPT_MODE);
    }

    protected static String encrypt(byte[] sSrc, String sKey) throws Exception {
        byte[] encrypt = cipher(sSrc, sKey, Cipher.ENCRYPT_MODE);
        return ByteUtil.byte2hex(encrypt).toLowerCase();
    }
}
