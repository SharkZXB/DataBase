package com.sharkz.database.greendao;

import android.app.Application;

import com.sharkz.database.greendao.sdk.DbHelper;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:16
 * 描    述 当前 Module 是 数据库之 GreenDAO
 * 修订历史：
 * ================================================
 */
public class GreenDAO {

    public static final boolean ENCRYPTED = true;   // 是否加密
    public static final String TEST_PWD = "test_pwd";
    public static final String DBName = "db_name";  // 数据库名字


    /**
     * 初始化 数据库
     */
    public static void initGreenDao(Application application, String dbName) {
        DbHelper.init(application, dbName);
    }

    /**
     * 配合 GreenDao 统一调用
     *
     * @return
     */
    public static DbHelper getDbHelper() {
        return DbHelper.getInstance();
    }


}

//  https://github.com/SharkZXB/WeMedia
// https://github.com/aserbao/AserbaosAndroid
// https://www.jianshu.com/p/53083f782ea2
// https://blog.csdn.net/qq_36699930/article/details/81540781
