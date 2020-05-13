package com.sharkz.database.greendao.sdk.tool;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/27  14:02
 * 描    述
 * 修订历史：
 * ================================================
 */
public class Utils {

    /**
     * 当前处理 之前的数据库没有加密
     *
     * @param context    上下文
     * @param dbName     数据库名字
     * @param passphrase 数据库密码
     * @throws IOException
     */
    public static void encrypt(Context context, String dbName, String passphrase) throws IOException {
        File originalFile = context.getDatabasePath(dbName);
        if (originalFile.exists()) {
            File newFile = File.createTempFile("sqlcipherutils", "tmp", context.getCacheDir());
            SQLiteDatabase db = SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);
            db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';", newFile.getAbsolutePath(), passphrase));
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.rawExecSQL("DETACH DATABASE encrypted;");
            int version = db.getVersion();
            db.close();
            db = SQLiteDatabase.openDatabase(newFile.getAbsolutePath(), passphrase, null, SQLiteDatabase.OPEN_READWRITE);
            db.setVersion(version);
            db.close();
            originalFile.delete();
            newFile.renameTo(originalFile);
        }
    }
}
