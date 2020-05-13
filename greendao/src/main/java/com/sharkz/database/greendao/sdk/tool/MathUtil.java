package com.sharkz.database.greendao.sdk.tool;

/*
 * Created by tcl on 18/10/24.
 */
public class MathUtil {

    /**
     * 字符串转化成为16进制字符串,这里转成16进制时加了1
     *
     * @param s
     * @return
     */
    public static String strToHex(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            ch = ch + 1;
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    /**
     * 16进制字符串转换成为string类型字符串，对应上面的方法，从字符串转16进制时减了1
     *
     * @param s
     * @return
     */
    public static String hexStrToStr(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & (Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16) - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 16进制字符串转换成为byte[]，对应上面的方法，转成byte时减了1,为了安全
     *
     * @param s
     * @return
     */
    public static byte[] hexStrToBytes(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & (Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16) - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword;
    }
}
