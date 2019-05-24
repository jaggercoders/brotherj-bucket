package com.brotherj.brotherjutil.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: zhanghl
 * @date: 2019/1/3 10:31
 */
public class AESUtil {

    private final static String AES_IV = "ed16234234kjd8d4";

    public static final String AES_KEY = "67a15a80bec844e9";

    // 加密
    public static final String encrypt(String sSrc,String sKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return Base64Utils.encode(encrypted);// 此处使用BASE64做转码。
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    // 解密
    public static final String decrypt(String sSrc,String sKey) {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64Utils.decode(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        String jiami = AESUtil.encrypt("aaa", "67a15a80bec844e9");
        System.out.println(jiami);
        System.out.println(AESUtil.decrypt(jiami, "67a15a80bec844e9"));

    }
}
