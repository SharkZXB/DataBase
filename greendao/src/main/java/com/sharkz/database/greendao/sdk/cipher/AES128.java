package com.sharkz.database.greendao.sdk.cipher;


import com.sharkz.database.greendao.sdk.tool.MathUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES128 {

    private byte[] byteKey;
    private byte[] byteIv;
    private String key = "3132313231323132313231323132313231323132313231323132313231323132";

    private static AES128 instance = null;

    private AES128() {
        key = MathUtil.hexStrToStr(key);//还原成01010101010101010101010101010101
        byteKey = MathUtil.hexStrToBytes(key);//生成最终的字节数组
        byteIv = MathUtil.hexStrToBytes(key);//生成最终的字节数组
    }

    public static AES128 getInstance() {
        if (instance == null)
            instance = new AES128();
        return instance;
    }

    public void setKey(byte[] key) {
        this.byteKey = key;
    }

    public void setIvParameter(byte[] iv) {
        this.byteIv = iv;
    }

    public byte[] string2ByteWithSplit(String str, String split) {
        byte[] byteArray = new byte[16];
        String[] strArray = str.split(split);
        for (int n = 0; n < strArray.length; n++) {
            byteArray[n] = Byte.valueOf(strArray[n]);
        }
        return byteArray;
    }

    public String encrptByCBC(String sSrc){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(byteKey, "AES");

            IvParameterSpec iv = new IvParameterSpec(byteIv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return new BASE64Encoder().encode(encrypted);
        }catch (Exception e){
            return null;
        }
    }

    public String decryptByCBC(String sSrc) {
        try {
            byte[] raw = byteKey;
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(byteIv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    // public String encrptByCBC(String sSrc, byte[] byteKey, byte[] byteIV) {
    //     try {
    //         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    //
    //         SecretKeySpec skeySpec = new SecretKeySpec(byteKey, "AES");
    //
    //         IvParameterSpec iv = new IvParameterSpec(byteIV);
    //         cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    //
    //         byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
    //         return new BASE64Encoder().encode(encrypted);
    //     }catch (Exception e){
    //         return null;
    //     }
    // }

    // public String decryptByCBC(String sSrc, byte[] byteKey, byte[] byteIV) throws Exception {
    //     try {
    //         byte[] raw = byteKey;
    //         SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    //         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    //         IvParameterSpec iv = new IvParameterSpec(byteIV);
    //         cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
    //
    //         byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
    //
    //         byte[] original = cipher.doFinal(encrypted1);
    //
    //         String originalString = new String(original, "utf-8");
    //         return originalString;
    //     } catch (Exception ex) {
    //         return null;
    //     }
    // }

    // public String encryptByCFB(String sSrc) {
    //     try {
    //         Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
    //
    //         SecretKeySpec skeySpec = new SecretKeySpec(byteKey, "AES");
    //
    //         IvParameterSpec iv = new IvParameterSpec(byteIv);
    //         cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    //
    //         byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
    //
    //         return new BASE64Encoder().encode(encrypted);
    //     }catch (Exception e){
    //         return null;
    //     }
    // }
    //
    //
    // public String decrptByCFB(String sSrc){
    //     try {
    //         byte[] raw = byteKey;
    //         SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    //         Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
    //         IvParameterSpec iv = new IvParameterSpec(byteIv);
    //         cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
    //
    //         byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
    //
    //         byte[] original = cipher.doFinal(encrypted1);
    //
    //         String originalString = new String(original, "utf-8");
    //         return originalString;
    //     } catch (Exception ex) {
    //         return null;
    //     }
    // }

}
