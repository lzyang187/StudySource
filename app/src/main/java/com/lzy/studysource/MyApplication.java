package com.lzy.studysource;

import android.app.Application;

/**
 * @author: cyli8
 * @date: 2019-10-15 18:32
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

}
