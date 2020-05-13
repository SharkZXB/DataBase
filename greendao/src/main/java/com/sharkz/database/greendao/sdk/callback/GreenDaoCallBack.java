package com.sharkz.database.greendao.sdk.callback;

import java.util.List;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  13:06
 * 描    述
 * 修订历史：
 * ================================================
 */
public interface GreenDaoCallBack<T> {

    /**
     * 异步任务开始
     */
    void startAsync();

    /**
     * 插入数据成功
     */
    void insertSuccess();

    /**
     * 删除数据
     */
    void deleteSuccess();

    /**
     * 插入/更新数据成功
     */
    void insertOrUpdateSuccess();

    /**
     * 更新数据
     */
    void updateSuccess();

    /**
     * 查询数据
     *
     * @param list
     */
    void querySuccess(List<T> list);

}
