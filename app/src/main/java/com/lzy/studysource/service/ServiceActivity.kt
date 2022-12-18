package com.lzy.studysource.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R
import com.lzy.studysource.toolbar.ToolbarActivity

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            val intent = Intent(this, StartService::class.java)
            startService(intent)
        }
        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            val intent = Intent(this, StartService::class.java)
            stopService(intent)
        }

        /**
         * 只要调用方和Service之间的连接没有断开，Service就会一直保持运行状态，直到被系统回收
         */
        findViewById<Button>(R.id.btn_bind).setOnClickListener {
            val intent = Intent(this, BindService::class.java)
            // BIND_AUTO_CREATE表示在Activity和Service进行绑定后自动创建Service
            bindService(intent, myServiceConnection, Service.BIND_AUTO_CREATE)
        }
        findViewById<Button>(R.id.btn_unbind).setOnClickListener {
            unbindService(myServiceConnection)
        }

        findViewById<Button>(R.id.jump).setOnClickListener {
            startActivity(Intent(this, ToolbarActivity::class.java))
        }

    }

    private lateinit var mDownloadBinder: BindService.DownloadBinder

    private val myServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e(TAG, "onServiceConnected: name = $name service = $service")
            mDownloadBinder = service as BindService.DownloadBinder
            mDownloadBinder.startDownload()
            mDownloadBinder.getProgress()
        }

        /**
         * 只有在Service的创建进程崩溃或者被杀掉的时候才会调用
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected: name = $name")
        }
    }


    companion object {
        private const val TAG = "ServiceActivity"
    }

}