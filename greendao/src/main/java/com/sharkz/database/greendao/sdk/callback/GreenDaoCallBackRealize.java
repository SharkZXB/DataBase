package com.sharkz.database.greendao.sdk.callback;

import java.util.List;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  13:36
 * 描    述 中转 子类重写需要的类就行了
 * 修订历史：
 * ================================================
 */
public class GreenDaoCallBackRealize<T> implements GreenDaoCallBack<T> {

    @Override
    public void startAsync() {

    }

    @Override
    public void insertSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void insertOrUpdateSuccess() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void querySuccess(List<T> list) {

    }
}
