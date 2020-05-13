package com.sharkz.database.greendao.sdk;

import android.app.Application;


import com.sharkz.database.greendao.dao.DaoMaster;
import com.sharkz.database.greendao.dao.DaoSession;
import com.sharkz.database.greendao.sdk.core.MySQLiteOpenHelper;
import com.sharkz.database.greendao.sdk.tool.Utils;

import net.sqlcipher.database.SQLiteException;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.io.IOException;

import static com.sharkz.database.greendao.GreenDAO.ENCRYPTED;
import static com.sharkz.database.greendao.GreenDAO.TEST_PWD;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:31
 * 描    述 多数据库创建 是否可以用 Map 保存数据(Session/DaoMaster)
 * 修订历史：
 * ================================================
 */
public class DbHelper {

    /**
     * 当前
     */
    private static DbHelper instance;

    /**
     * 自带异步操作类
     * https://blog.csdn.net/yide55/article/details/50601504?utm_source=blogxgwz6
     */
    private AsyncSession mAsyncSession;

    /**
     * 管理特定模式的所有可用DAO对象，您可以使用其中一个getter方法获取该对象。
     * DaoSession还提供了一些通用的持久性方法，如实体的插入，加载，更新，刷新和删除。
     * 通过当前可以获取到特定的DAOSection对象 --> 操作数据
     */
    private DaoSession mDaoSession;

    /**
     * 创建数据库的工具
     */
    private MySQLiteOpenHelper mOpenHelper;

    /**
     * 它里边实际上是保存数据库的对象
     * DaoMaster保存数据库对象（SQLiteDatabase）并管理特定模式的DAO类（而不是对象）。
     * 它有静态方法来创建表或删除它们。它的内部类OpenHelper和DevOpenHelper是SQLiteOpenHelper实现，
     * 它们在SQLite数据库中创建模式。
     */
    private DaoMaster mDaoMaster;


    // =============================================================================================


    /**
     * TODO 数据库的初始化
     * 最好在Application中初始化
     *
     * @param application  application
     * @param dataBaseName 数据库名字
     * @return {@link DbHelper#instance}
     */
    public static DbHelper init(Application application, String dataBaseName) {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    //单例模式之双重检测：线程一在此之前线程二到达了位置[1],如果此处不二次判断，那么线程二执行到这里的时候还会重新new
                    instance = new DbHelper(application, dataBaseName);
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造方法
     *
     * @param application  上下文
     * @param dataBaseName 数据库名字
     */
    private DbHelper(Application application, String dataBaseName) {
        // 获取可以打开数据库的帮助类
        mOpenHelper = new MySQLiteOpenHelper(application, dataBaseName);
        // 下面是之前没有加密 后面加密处理的 解决办法
        if (ENCRYPTED) {
            try {
                mDaoMaster = new DaoMaster(mOpenHelper.getEncryptedWritableDb(TEST_PWD));
            } catch (SQLiteException e) {
                try {
                    Utils.encrypt(application.getApplicationContext(), dataBaseName, TEST_PWD);
                    mDaoMaster = new DaoMaster(mOpenHelper.getEncryptedWritableDb(TEST_PWD));
                } catch (IOException e1) {
                    e1.printStackTrace();
                    mDaoMaster = new DaoMaster(mOpenHelper.getWritableDatabase());
                }
            }
        } else {
            mDaoMaster = new DaoMaster(mOpenHelper.getWritableDatabase());
        }
        // IdentityScopeType.None --> 干嘛的
        mDaoSession = mDaoMaster.newSession(IdentityScopeType.None);

        // 异步操作
        mAsyncSession = mDaoSession.startAsyncSession();
    }


    // =============================================================================================

    /**
     * 获取当前实例
     *
     * @return {@link DbHelper#instance}
     */
    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    throw new UnsupportedOperationException("Didn't finish the " + "DbHelper" + " init");
                }
            }
        }
        return instance;
    }


    // =============================================================================================


    /**
     * 获取当前数据库的Section
     *
     * @return {@link DbHelper#mDaoSession}
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * @return {@link DbHelper#mDaoMaster}
     */
    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    /**
     * 获取异步处理操作类
     *
     * @return {@link DbHelper#mAsyncSession}
     */
    public AsyncSession getAsyncSession() {
        return mAsyncSession;
    }

    /**
     * 关闭连接
     */
    public void closeConnection() {
        clearAllDaoSession();
        closeHelper();
    }

    private void closeHelper() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
    }

    /**
     * 清除整个数据库的缓存session，没有缓存对象返回
     */
    public void clearAllDaoSession() {
        mDaoSession.clear();
    }

    /**
     * 清除单个Dao的identity scope,这样就会清除缓存了
     *
     * @param dao
     */
    public void clearDaoSession(AbstractDao dao) {
        dao.detachAll();
    }

    /**
     * 删除所有表 谨慎使用
     */
    public void deleteAllTable() {
        DaoMaster.dropAllTables(mDaoMaster.getDatabase(), true);
        //必须要重新创建表,不然重新进行数据库操作的时候（crud）会报找不到数据库表的错误：
        DaoMaster.createAllTables(mDaoMaster.getDatabase(), true);
    }

}
