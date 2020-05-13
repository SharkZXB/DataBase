package com.sharkz.database.greendao.sdk.bean;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:31
 * 描    述 所有JavaBean的基类 统一规范 所有的基类都必须继承当前
 * 修订历史：
 * ================================================
 */
public class BaseDbBean implements Serializable, Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
