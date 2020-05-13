package com.sharkz.database.greendao.sdk.core;

import android.content.Context;

import com.sharkz.database.greendao.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:38
 * 描    述 这个是打开数据库的帮助类
 * 修订历史：
 * ================================================
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //   数据库升级
        // TODO: 2016/3/24 注意把所新版本的表的xxDao都添加到这里
        // MigrationHelper.getInstance().migrate(db, UserDao.class);
        // MigrationHelper.migrate(db, AppLaunchBeanDao.class);
    }

}
