package com.sharkz.database.greendao.sdk.core;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:37
 * 描    述
 * 修订历史：
 * ================================================
 */
public class CheckNullProperty {

    /**
     * null判断
     */
    public static WhereCondition eq(Property property, Object value) {
        if (value == null) {
            value = "";
        }
        return property.eq(value);
    }

}
