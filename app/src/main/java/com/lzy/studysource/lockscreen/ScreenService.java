package com.lzy.studysource.lockscreen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ScreenService extends Service {
    public ScreenService() {

    }

    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.d(ScreenActivity.TAG, "onReceive: 监听到锁屏广播");
                Intent lockIntent = new Intent(context, ScreenActivity.class);
                lockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(lockIntent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenOffReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mScreenOffReceiver != null) {
            unregisterReceiver(mScreenOffReceiver);
        }
        Log.d(ScreenActivity.TAG, "onDestroy: ScreenService销毁");
    }

}
