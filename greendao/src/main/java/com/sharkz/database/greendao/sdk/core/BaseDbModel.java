package com.sharkz.database.greendao.sdk.core;

import android.text.TextUtils;

import com.sharkz.database.greendao.dao.DaoSession;
import com.sharkz.database.greendao.sdk.DbHelper;
import com.sharkz.database.greendao.sdk.callback.GreenDaoCallBack;
import com.sharkz.database.greendao.sdk.cipher.AES128;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:39
 * 描    述 Dao增删该查
 * 修订历史：
 * ================================================
 */
public abstract class BaseDbModel<T> {

    /**
     *
     */
    private static final int ASC = 1;
    private static final int DESC = 2;

    /**
     * DaoSession 管理gen里生成的所有的Dao对象里边带有基本的增删改查的方法
     */
    private static DaoSession getDaoSession() {
        return DbHelper.getInstance().getDaoSession();
    }

    /**
     * 获取Dao 来进行更加高级的插入，查询，删除，更新等操作
     *
     * @param entityClass 实体类
     * @param <J>         DAO
     * @return J 类型Dao
     */
    private static <J extends AbstractDao> J getDao(Class<? extends Object> entityClass) {
        return (J) getDaoSession().getDao(entityClass);
    }



    /*----------------------------------------------插入数据---------------------------------------*/
    /*----------------------------------------------插入数据---------------------------------------*/
    /*----------------------------------------------插入数据---------------------------------------*/


    /**
     * 同步插入数据
     *
     * @param item T
     */
    public void insert(T item) {
        item = encrypt(item); // 解密
        getDaoSession().insert(item);
    }

    /**
     * 异步插入数据
     *
     * @param item     entirety
     * @param callBack 回调
     */
    public void insertAsync(T item, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        T copyItem = encrypt(item); // 解密
        DbHelper.getInstance().getAsyncSession().runInTx(() -> {
            getDaoSession().insert(copyItem);
            if (callBack != null) {
                callBack.insertSuccess();
            }
        });
    }

    /**
     * 同步批量插入
     *
     * @param items T
     */
    public void insert(Class<T> cls, List<T> items) {
        items = encrypt(items);
        getDao(cls).insertInTx(items);
    }

    /**
     * 异步批量插入
     *
     * @param items    T
     * @param callBack 结果回调
     */
    public void insertAsync(List<T> items, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        items = encrypt(items);
        List<T> finalItems = items;
        getDaoSession().startAsyncSession().runInTx(() -> {
            for (T item : finalItems) {
                getDaoSession().insert(item);
            }
            if (callBack != null) {
                callBack.insertSuccess();
            }
        });
    }

    /**
     * 同步插入/升级
     *
     * @param item T
     */
    public void insertOrUpdate(T item) {
        item = encrypt(item);
        getDaoSession().insertOrReplace(item);
    }

    /**
     * 异步插入/升级
     *
     * @param item
     * @param callBack
     */
    public void insertOrUpdate(T item, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        T copyItem = encrypt(item);
        DbHelper.getInstance().getAsyncSession().runInTx(() -> {
            getDaoSession().insertOrReplace(copyItem);
            if (callBack != null) {
                callBack.insertOrUpdateSuccess();
            }
        });
    }


    /**
     * 同步插入/更新数据 批量
     *
     * @param items list
     */
    public void insertOrUpdate(Class<T> cls, List<T> items) {
        items = encrypt(items);
        getDao(cls).insertOrReplaceInTx(items);
    }

    /**
     * 异步插入/更新数据 批量
     *
     * @param items    list
     * @param callBack 回调
     */
    public void insertOrUpdateAsync(List<T> items, GreenDaoCallBack<T> callBack) {
        items = encrypt(items);
        List<T> finalItems = items;
        getDaoSession().startAsyncSession().runInTx(() -> {
            for (T item : finalItems) {
                getDaoSession().insertOrReplace(item);
            }
            if (callBack != null) {
                callBack.insertOrUpdateSuccess();
            }
        });
    }

    /**
     * 异步插入/更新数据 批量
     *
     * @param items list
     */
    public void insertOrUpdate(Class<T> cls, List<T> items, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        List<T> copyItems = encrypt(items);
        DbHelper.getInstance().getAsyncSession().runInTx(() -> {
            getDao(cls).insertOrReplaceInTx(copyItems);
            if (callBack != null) {
                callBack.insertOrUpdateSuccess();
            }
        });
    }



    /*----------------------------------------------删除数据---------------------------------------*/
    /*----------------------------------------------删除数据---------------------------------------*/
    /*----------------------------------------------删除数据---------------------------------------*/


    /**
     * 通过key删除数据
     *
     * @param cls 那个类型的Dao
     * @param key key
     */
    public void deleteByKey(Class cls, Long key) {
        getDao(cls).deleteByKey(key);
    }

    /**
     * 同步删除一个数据
     *
     * @param item
     * @param <T>
     */
    public <T> void delete(T item) {
        getDaoSession().delete(item);
    }

    /**
     * 异步删除一个数据
     *
     * @param item
     * @param <T>
     */
    public <T> void delete(T item, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        DbHelper.getInstance().getAsyncSession().runInTx(() -> {
            getDaoSession().delete(item);
            if (callBack != null) {
                callBack.deleteSuccess();
            }
        });
    }


    /**
     * 同步批量删除数据
     *
     * @param cls
     * @param items
     * @param <T>
     */
    public <T> void delete(Class cls, List<T> items) {
        getDao(cls).deleteInTx(items);
    }

    /**
     * 异步批量删除数据
     *
     * @param cls
     * @param items
     * @param <T>
     */
    public <T> void delete(Class cls, List<T> items, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        DbHelper.getInstance().getAsyncSession().runInTx(() -> {
            getDao(cls).deleteInTx(items);
            if (callBack != null) {
                callBack.deleteSuccess();
            }
        });
    }


    /**
     * 同步清空表中的所有数据
     *
     * @param entityClass
     */
    public void deleteAll(Class entityClass) {
        getDaoSession().deleteAll(entityClass);
    }

    /**
     * 异步清空表中的所有数据
     *
     * @param entityClass
     */
    public void deleteAllAsync(Class entityClass, GreenDaoCallBack callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        DbHelper.getInstance().getAsyncSession().runInTx(() -> {
            getDaoSession().deleteAll(entityClass);
            if (callBack != null) {
                callBack.deleteSuccess();
            }
        });
    }


    /*----------------------------------------------更新数据---------------------------------------*/
    /*----------------------------------------------更新数据---------------------------------------*/
    /*----------------------------------------------更新数据---------------------------------------*/


    public void update(T item) {
        item = encrypt(item);
        getDaoSession().update(item);
    }

    /**
     * 更新entity 集合
     *
     * @param items
     */
    public void update(Class cls, List<T> items) {
        items = encrypt(items);
        getDao(cls).updateInTx(items);
    }

    /**
     * 异步更新
     *
     * @param items
     * @param callBack
     */
    public void updateAsync(List<T> items, GreenDaoCallBack<T> callBack) {
        if (callBack != null) {
            callBack.startAsync();
        }
        items = encrypt(items);
        List<T> finalItems = items;
        getDaoSession().startAsyncSession().runInTx(() -> {
            for (T item : finalItems) {
                getDaoSession().update(item);
            }
            if (callBack != null) {
                callBack.updateSuccess();
            }
        });
    }


    /*----------------------------------------------查询数据---------------------------------------*/
    /*----------------------------------------------查询数据---------------------------------------*/
    /*----------------------------------------------查询数据---------------------------------------*/


    /**
     * 查询单个entity
     *
     * @param key 主键
     * @return
     */
    public T query(Class<T> cls, Long key) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        T load = (T) (getDao(cls).load(key));
        load = decrypt(load);
        return load;
    }

    /**
     * 同步查询
     *
     * @return
     */
    public List<T> queryAll(Class<T> cls) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        List list = getDao(cls).loadAll();
        list = decrypt(list);
        return list;
    }

    /**
     * 异步查询
     *
     * @param cls
     * @param callBack
     */
    public void queryAllAsync(Class<T> cls, GreenDaoCallBack<T> callBack) {
        callBack.startAsync();
        DbHelper.getInstance().getAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<T> list = getDao(cls).loadAll();
                list = decrypt(list);
                if (callBack != null) {
                    callBack.querySuccess(list);
                }
            }
        });
    }

//    /**
//     * 异步查询
//     *
//     * @param callBack
//     */
//    public void queryAllAsync(Class<T> cls, GreenDaoCallBack<T> callBack) {
//        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
//        getDaoSession().startAsyncSession().runInTx(() -> {
//            List entityList = getDao(cls).loadAll();
//            entityList = decrypt(entityList);
//            if (callBack != null) {
//                callBack.querySuccess(entityList);
//            }
//        });
//    }

    /**
     * 查询多个数据
     *
     * @param whereConditions
     * @return
     */
    public T querySingle(Class<T> cls, WhereCondition[] whereConditions) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        T unique = tQueryBuilder.limit(1).unique();
        unique = decrypt(unique);
        return unique;
    }

    public T querySingleAndOr(Class<T> cls, WhereCondition[] whereConditions, WhereCondition orCond1, WhereCondition orCond2) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(orCond1, orCond2));
        T unique = tQueryBuilder.limit(1).unique();
        unique = decrypt(unique);
        return unique;
    }

    /**
     * 根据条件查询数据，条件都是and
     *
     * @param cls
     * @param whereConditions
     * @return
     */
    public List<T> queryList(Class<T> cls, WhereCondition[] whereConditions) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        List<T> list = tQueryBuilder.list();
        list = decrypt(list);
        return list;
    }


    public List<T> queryList(Class<T> cls, WhereCondition[] whereConditions,
                             WhereCondition orCond1, WhereCondition orCond2) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(orCond1, orCond2));
        List<T> list = tQueryBuilder.list();
        list = decrypt(list);
        return list;
    }

    public List<T> queryList(Class<T> cls, WhereCondition[] whereConditions,
                             WhereCondition orCond1, WhereCondition orCond2, WhereCondition... orMore) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(orCond1, orCond2, orMore));
        List<T> list = tQueryBuilder.list();
        list = decrypt(list);
        return list;
    }

    /**
     * 条件查询并正序排序
     */
    public List<T> queryListWithOrderAsc(Class<T> cls, WhereCondition[] whereConditions,
                                         Property[] properties) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        List<T> list = tQueryBuilder.orderAsc(properties).list();
        list = decrypt(list);
        return list;
    }


    public List<T> queryListWithOrderAsc(Class<T> cls, WhereCondition[] whereConditions,
                                         WhereCondition orCon1, WhereCondition orCon2,
                                         Property[] properties) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(orCon1, orCon2));
        List<T> list = tQueryBuilder.orderAsc(properties).list();
        list = decrypt(list);
        return list;
    }


    public List<T> queryListWithOrderAsc(Class<T> cls, WhereCondition[] whereConditions, Property[] properties,
                                         WhereCondition orCon1, WhereCondition orCon2, WhereCondition... orConMore) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(orCon1, orCon2, orConMore));
        List<T> list = tQueryBuilder.orderAsc(properties).list();
        list = decrypt(list);
        return list;
    }


    /**
     * 条件查询并逆序排序
     */
    public List<T> queryListWithOrderDesc(Class<T> cls, WhereCondition[] whereConditions,
                                          Property[] properties) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        List<T> list = tQueryBuilder.orderDesc(properties).list();
        list = decrypt(list);
        return list;
    }

    /**
     * 根据条件查询数据，条件都是or
     */
    public List<T> queryListOr(Class<T> cls, WhereCondition cond1, WhereCondition cond2, WhereCondition... condMore) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        tQueryBuilder.whereOr(cond1, cond2, condMore);
        List<T> list = tQueryBuilder.list();
        list = decrypt(list);
        return list;
    }

    /**
     * @param cls             类型
     * @param or1             或条件
     * @param or2             或条件
     * @param whereConditions 与条件
     */
    public List<T> queryListOrAnd(Class<T> cls, WhereCondition or1, WhereCondition or2,
                                  WhereCondition[] whereConditions) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (whereConditions != null) {
            for (WhereCondition whereCondition : whereConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(or1, or2));
        List<T> list = tQueryBuilder.list();
        list = decrypt(list);
        return list;
    }


    public List<T> queryListOrAnd(Class<T> cls, WhereCondition[] andConditions,
                                  WhereCondition[] orConditions) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        //先加and命令
        if (andConditions != null) {
            for (WhereCondition whereCondition : andConditions) {
                tQueryBuilder.where(whereCondition);
            }
        }
        //再加or命令
        if (orConditions.length == 2) {
            tQueryBuilder.where(tQueryBuilder.or(orConditions[0], orConditions[1]));
        } else {
            WhereCondition[] extraConditions = new WhereCondition[orConditions.length - 2];
            for (int i = 2; i < orConditions.length; i++) {
                extraConditions[i - 2] = orConditions[i];
            }
            tQueryBuilder.where(tQueryBuilder.or(orConditions[0], orConditions[1], extraConditions));
        }
        List<T> list = tQueryBuilder.list();
        list = decrypt(list);
        return list;
    }

    /**
     * @param cls        类型
     * @param or1        或条件
     * @param or2        或条件
     * @param and        与条件
     * @param properties 排序属性
     */
    public List<T> queryListOrAndOrderAsc(Class<T> cls, WhereCondition or1, WhereCondition or2,
                                          WhereCondition[] and, Property[] properties) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (and != null) {
            for (WhereCondition whereCondition : and) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(or1, or2));
        List<T> list = tQueryBuilder.orderAsc(properties).list();
        list = decrypt(list);
        return list;
    }

    public List<T> queryListOrAndOrderAsc(Class<T> cls, WhereCondition or1, WhereCondition or2,
                                          WhereCondition or3, WhereCondition or4,
                                          WhereCondition[] and, Property[] properties) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (and != null) {
            for (WhereCondition whereCondition : and) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(or1, or2));
        tQueryBuilder.where(tQueryBuilder.or(or3, or4));
        List<T> list = tQueryBuilder.orderAsc(properties).list();
        list = decrypt(list);
        return list;
    }


    public List<T> queryListOrAndOrderAsc(Class<T> cls, WhereCondition[] and, Property[] properties, WhereCondition or1_1, WhereCondition or1_2,
                                          WhereCondition or2_1, WhereCondition or2_2, WhereCondition... or2_more) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
        if (and != null) {
            for (WhereCondition whereCondition : and) {
                tQueryBuilder.where(whereCondition);
            }
        }
        tQueryBuilder.where(tQueryBuilder.or(or1_1, or1_2));
        tQueryBuilder.where(tQueryBuilder.or(or2_1, or2_1, or2_more));
        List<T> list = tQueryBuilder.orderAsc(properties).list();
        list = decrypt(list);
        return list;
    }


    /**
     * 异步查询,根据条件查询数据，条件都是and
     *
     * @param cls             要查询的数据库类
     * @param whereConditions 条件数组
     * @param callBack        回调
     */
    public void queryListAsync(Class<T> cls, WhereCondition[] whereConditions, GreenDaoCallBack<T> callBack) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        if (callBack != null) {
            callBack.startAsync();
        }
        getDaoSession().startAsyncSession().runInTx(() -> {
            QueryBuilder<T> tQueryBuilder = getDao(cls).queryBuilder();
            if (whereConditions != null) {
                for (WhereCondition whereCondition : whereConditions) {
                    tQueryBuilder.where(whereCondition);
                }
            }
            if (callBack != null) {
                List<T> list = tQueryBuilder.list();
                list = decrypt(list);
                callBack.querySuccess(list);
            }
        });
    }

    /**
     * 异步查询,根据条件查询数据，条件都是and
     *
     * @param cls             要查询的数据库类
     * @param whereConditions 条件数组
     * @param order
     * @param orderty
     * @param callBack        回调
     */
    public void queryListAsync(Class<T> cls, WhereCondition[] whereConditions, int order, Property orderty, GreenDaoCallBack<T> callBack) {
        // DbHelper.getInstance().clearDaoSessing(getDao(cls));
        if (callBack != null) {
            callBack.startAsync();
        }
        getDaoSession().startAsyncSession().runInTx(() -> {
            QueryBuilder<T> queryBuilder = getDao(cls).queryBuilder();
            if (whereConditions != null) {
                for (WhereCondition whereCondition : whereConditions) {
                    queryBuilder.where(whereCondition);
                }
            }
            if (order == ASC) {
                queryBuilder.orderAsc(orderty);
            } else if (order == DESC) {
                queryBuilder.orderDesc(orderty);
            }
            if (callBack != null) {
                List<T> list = queryBuilder.list();
                list = decrypt(list);
                callBack.querySuccess(list);
            }
        });
    }


    /*-------------------------------------------获取数据总数---------------------------------------*/

    /**
     * 获取entity在数据库中的条数
     *
     * @return
     */
    public <T> long getCount(Class<T> cls) {
        return getDao(cls).count();
    }



    /*----------------------------------------------加解密部分-------------------------------------*/
    /*----------------------------------------------加解密部分-------------------------------------*/
    /*----------------------------------------------加解密部分-------------------------------------*/


    /**
     * 加密
     *
     * @param item
     * @return
     */
    public List<T> encrypt(List<T> item) {
        List<T> list = new ArrayList<>();
        for (T t : item) {
            T encrypt = encrypt(t);
            list.add(encrypt);
        }
        return list;
    }

    /**
     * 解密
     *
     * @param item
     * @return
     */
    public List<T> decrypt(List<T> item) {
        List<T> list = new ArrayList<>();
        for (T t : item) {
            T decrypt = decrypt(t);
            list.add(decrypt);
        }
        return list;
    }

    /**
     * 加密
     *
     * @param item
     * @return
     */
    private T encrypt(T item) {
        return item;
    }

    /**
     * 解密
     *
     * @param item
     * @return
     */
    private T decrypt(T item) {
        return item;
    }

    /**
     * 解密字符串
     *
     * @param object str
     * @return 解密过后的 str
     */
    public String decrypt(String object) {
        if (!TextUtils.isEmpty(object)) {
            object = AES128.getInstance().decryptByCBC(object);
        }
        return object;
    }

    /**
     * 加密字符串
     *
     * @param object str
     * @return 加密过后的str
     */
    public String encrypt(String object) {
        if (!TextUtils.isEmpty(object)) {
            object = AES128.getInstance().encrptByCBC(object);
        }
        return object;
    }
}
