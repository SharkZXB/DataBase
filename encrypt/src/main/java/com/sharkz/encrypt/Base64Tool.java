package com.sharkz.encrypt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-02-11  12:23
 * 描    述  Base64严格地说，属于编码格式，而非加密算法,用于二进制内容转换为可编辑的文本内容。
 * Base64也会经常用作一个简单的“加密”来保护某些数据，而真正的加密通常都比较繁琐。
 * 编码后的内容，是由64个字符（大小写英文字母 0-9 + / (= 补位符，填充字符)）组成的序列，成为Base64。可逆的编码方式。
 * 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
 * 修订历史：
 * ================================================
 */
public class Base64Tool {

    /**
     * 字符Base64加密
     *
     * @param str
     * @return
     */
    public static String encodeToString(String str) {
        return Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }

    /**
     * 字符Base64解密
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        return new String(Base64.decode(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
    }


    // =============================================================================================

    // Android端使用Base64加密解密时应使用Base64.NO_WRAP
    // 其他几种加密解密方式的含义：
    // CRLF：这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
    // DEFAULT：这个参数是默认，使用默认的方法来加密
    // NO_PADDING：这个参数是略去加密字符串最后的“=”
    // NO_WRAP：这个参数意思是略去所有的换行符（设置后CRLF就没用了）
    // URL_SAFE：这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/


    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path 图片文件路径
     * @return
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 将Base64编码转换为图片 （保存图片）
     *
     * @param base64Str base64 字符串
     * @param path      保存图片的路径
     * @return true:保存成功 / false:保存失败
     */
    public static boolean base64ToFile(String base64Str, String path) {
        byte[] data = Base64.decode(base64Str, Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * bitmap 转换成 base64字符串
     *
     * @param bitmap bitmap
     * @return
     */
    public String bitmap2String(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 将字符串转换成 Bitmap （不保存图片）
     *
     * @param base64Str base64 字符串
     * @return
     */
    public Bitmap string2Bitmap(String base64Str) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(base64Str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
