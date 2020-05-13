package com.sharkz.database.greendao.sdk.demo;

import com.sharkz.database.greendao.dao.TestGreenDAOJavaBeanDao;
import com.sharkz.database.greendao.sdk.bean.TestGreenDAOJavaBean;
import com.sharkz.database.greendao.sdk.core.BaseDbModel;
import com.sharkz.database.greendao.sdk.core.CheckNullProperty;

import org.greenrobot.greendao.query.WhereCondition;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  13:45
 * 描    述 这个是一个Demo 新写的JavaBean 按照这个来就可以了 方法直接调用父类的
 * 修订历史：
 * ================================================
 */
public class TestGreenDAOJavaBeanModel extends BaseDbModel<TestGreenDAOJavaBean> {

    private static TestGreenDAOJavaBeanModel instance;

    public synchronized static TestGreenDAOJavaBeanModel getInstance() {
        if (instance == null) {
            synchronized (TestGreenDAOJavaBeanModel.class) {
                instance = new TestGreenDAOJavaBeanModel();
            }
        }
        return instance;
    }

    private TestGreenDAOJavaBeanModel() {

    }


    /**
     * 条件查询
     */
    public TestGreenDAOJavaBean getDbBean(String url) {
        return querySingle(TestGreenDAOJavaBean.class,
                new WhereCondition[]{CheckNullProperty.eq(TestGreenDAOJavaBeanDao.Properties.Url, url)});
    }

}
