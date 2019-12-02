package com.lzy.studysource;

import android.app.Application;
import android.content.Intent;

import com.lzy.studysource.lockscreen.ScreenService;

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
        startService(new Intent(this, ScreenService.class));
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

}
