package com.lzy.studysource.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * Activity和Service进行通信
 * 1、任何一个Service在整个应用程序范围内都是通用的，即MyService不仅可以和MainActivity绑定，
 * 还可以和任何一个其他的Activity进行绑定，而且在绑定完成后，它们都可以获取相同的DownloadBinder实例
 */
class BindService : Service() {

    private val mDownloadBinder by lazy {
        DownloadBinder()
    }

    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d(TAG, "startDownload executed")
        }

        fun getProgress(): Int {
            Log.d(TAG, "getProgress executed")
            return 50
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e(TAG, "onBind: $intent")
        return mDownloadBinder
    }

    /**
     * 如果我们希望Service一旦启动就立刻去执行某个动作，就可以将逻辑写在onStartCommand()方法里
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand: intent = $intent flags = $flags startId = $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 回收那些不再使用的资源
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }

    companion object {
        private const val TAG = "BindService"
    }
}