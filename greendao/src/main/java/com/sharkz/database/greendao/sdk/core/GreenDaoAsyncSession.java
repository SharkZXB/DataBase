package com.sharkz.database.greendao.sdk.core;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  13:14
 * 描    述
 * 修订历史：
 * ================================================
 */
public class GreenDaoAsyncSession {

    /*
    AsyncSession：
    GreenDao提供一个异步操作的统一接口类AsyncSession，它提供了你所需要的所有异步操作方法。
    你可以通过调用DaoSession#startAsyncSession()来获得一个AsyncSession实例。

    public AsyncSession startAsyncSession() {
        return new AsyncSession(this);
    }

    在AsyncSession的构造函数中，会赋值两个全局变量：一个是传参进来的DaoSession，另一个是创建的AsyncOperationExecutor对象。

    public AsyncSession(AbstractDaoSession daoSession) {
        this.daoSession = daoSession;
        this.executor = new AsyncOperationExecutor();
    }

    那么AsyncOperationExecutor是什么？
    AsyncOperationExecutor是一条管理着阻塞队列的线程，由它来进行实际的db操作，它的内部实现有点类似于Looper（后面再详解）。
    AsyncSession作为一个接口类，会将所有异步任务组装发布给AsyncOperationExecutor进行具体的操作。

    有异步任务来时，AsyncSession会将异步任务封装成AsyncOperation对象，再将AsyncOperation对象加入到AsyncOperationExecutor对象的阻塞队列中，
    再走AsyncOperationExecutor的内部逻辑。


    private AsyncOperation enqueueDatabaseOperation(OperationType type, Object param, int flags) {
        SQLiteDatabase database = daoSession.getDatabase();
        AsyncOperation operation = new AsyncOperation(type, null, database, param, flags | sessionFlags);
        executor.enqueue(operation);
        return operation;
    }

    这个AsyncOperation包含有下列信息：
    db操作的类型OperationType ：增、删、改、查（这里细分的多，会根据具体操作来返回对应的结果）；
    SQLiteDatabase 实例；
    操作的参数parameter：sql语句的where判断；


    每一个AsyncSession对象都拥有一个AsyncOperationExecutor 实例，，而每一个AsyncOperationExecutor 对象都管理着一个阻塞队列。
    也就是说，在每个类里头创建的AsyncSession对象将在一条线程中处理一个队列的工作，这样至少能保证当前类中只会创建一条工作线程循环处理非UI操作。

    AsyncOperationExecutor中工作流程：
    AsyncOperationExecutor本身运行于线程池生成的某一条线程中，管理着BlockingQueue，在有AsyncOperation添加时，开启任务执行，
    设置标记executorRunning =true：表示线程为运行状态。
    AsyncOperationExecutor运行时不停的从BlockingQueue中取出有AsyncOperation进行预定义的增删改查操作(executeOperationAndPostCompleted(),
    如队列为空则线程阻塞），在操作完成后将结果赋值给有AsyncOperation.result变量（如无则不赋值），再将带任务结果的AsyncOperation返回给监听类。









    */

}
