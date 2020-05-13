package com.sharkz.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sharkz.database.greendao.sdk.demo.TestGreenDAOJavaBeanModel;
import com.sharkz.database.greendao.sdk.bean.TestGreenDAOJavaBean;
import com.sharkz.database.greendao.sdk.callback.GreenDaoCallBackRealize;

import java.util.List;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  11:15
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Shark_DAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 100; i++) {
            TestGreenDAOJavaBean test = new TestGreenDAOJavaBean();
            test.setUrl("url=" + i);
            final int finalI = i;
            TestGreenDAOJavaBeanModel.getInstance().insertAsync(test, new GreenDaoCallBackRealize<TestGreenDAOJavaBean>() {
                @Override
                public void startAsync() {
                    super.startAsync();
                    Log.i(TAG, "startAsync: 开始插入数据了 i=" + finalI);
                }

                @Override
                public void insertSuccess() {
                    super.insertSuccess();
                    Log.i(TAG, "startAsync: 插入数据完成 i=" + finalI);
                }
            });

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queryData();
            }
        }, 2000);
    }

    private void queryData() {
        TestGreenDAOJavaBeanModel.getInstance().queryAllAsync(TestGreenDAOJavaBean.class, new GreenDaoCallBackRealize<TestGreenDAOJavaBean>() {
            @Override
            public void startAsync() {
                super.startAsync();
                Log.i(TAG, "startAsync: 开始查询数据了 ");
            }

            @Override
            public void querySuccess(List<TestGreenDAOJavaBean> list) {
                super.querySuccess(list);
                Log.i(TAG, "querySuccess: 查询数据结束 size=" + list.size());
            }
        });


    }
}
