package com.lzy.studysource.lockscreen;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;

public class ScreenActivity extends AppCompatActivity {
    public static final String TAG = "LockScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉系统锁屏页
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Window window = getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            }
        } else {
            KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("LockScreen");
            lock.disableKeyguard();
        }
        setContentView(R.layout.activity_screen);

        SwipeBackLayout layout = findViewById(R.id.swipebacklayout);
        layout.setSwipeBackListener(new SwipeBackLayout.SwipeBackFinishActivityListener(this));
        Log.d(TAG, "onCreate: 锁屏页启动");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 锁屏页销毁");
    }


}
