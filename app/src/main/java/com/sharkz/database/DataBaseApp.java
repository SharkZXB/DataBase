package com.sharkz.database;

import android.app.Application;

import com.sharkz.database.greendao.GreenDAO;
import com.sharkz.database.greendao.sdk.DbHelper;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  13:58
 * 描    述
 * 修订历史：
 * ================================================
 */
public class DataBaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper.init(this, GreenDAO.DBName);
    }
}
