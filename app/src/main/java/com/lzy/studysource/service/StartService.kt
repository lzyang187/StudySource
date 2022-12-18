package com.lzy.studysource.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * 1、适合执行那些不需要和用户交互而且还要求长期运行的任务。Service的运行不依赖于任何用户界面，即使程序被切换到后台，或
 * 者用户打开了另外一个应用程序，Service仍然能够保持正常运行
 * 2、Service并不是运行在一个独立的进程当中的，而是依赖于创建Service时所在的应用程序进程。当某个应用程序进程被杀掉时，
 * 所有依赖于该进程的Service也会停止运行
 * 3、Service并不会自动开启线程，所有的代码都是默认运行在主线程当中的。也就是说，我们需要在Service的内部手动创建子线程，
 * 并在这里执行具体的任务，否则就有可能出现主线程被阻塞的情况
 * 4、从Android 8.0系统开始，应用的后台功能被大幅削减。现在只有当应用保持在前台可见状态的情况下，
 * Service才能保证稳定运行，一旦应用进入后台之后，Service随时都有可能被系统回收
 * 5、如果你真的非常需要长期在后台执行一些任务，可以使用前台Service或者WorkManager
 *
 */
class StartService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.e(TAG, "onBind: ")
        return null
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
        private const val TAG = "MyService"
    }
}