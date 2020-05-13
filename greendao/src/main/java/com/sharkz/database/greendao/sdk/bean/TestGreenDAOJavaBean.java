package com.sharkz.database.greendao.sdk.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:26
 * 描    述 每一个实体类 就是一张表
 * 修订历史：
 * ================================================
 */

/**
 * // @Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
 * // @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 * // @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，它默认就是自增的,如果false就会使用旧值 。
 * // @Property：可以自定义字段名，注意外键不能使用该属性
 * // @NotNull：属性不能为空 @Transient：使用该注释的属性不会被存入数据库的字段中
 * // @Unique：该属性值必须在数据库中是唯一值
 * // @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 */
@Entity
public class TestGreenDAOJavaBean extends BaseDbBean {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String url;

    private int taskId;


    @Generated(hash = 1824534855)
    public TestGreenDAOJavaBean(Long id, @NotNull String url, int taskId) {
        this.id = id;
        this.url = url;
        this.taskId = taskId;
    }

    @Generated(hash = 916624209)
    public TestGreenDAOJavaBean() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
