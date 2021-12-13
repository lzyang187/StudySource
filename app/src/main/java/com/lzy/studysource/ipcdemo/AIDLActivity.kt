package com.lzy.studysource.ipcdemo

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class AIDLActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AIDLActivity"
    }

    private var mStudentManager: IStudentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidlactivity)
        val addBtn = findViewById<Button>(R.id.add)
        val queryBtn = findViewById<Button>(R.id.query)
        addBtn.setOnClickListener {
            mStudentManager?.addStudent(Student(1, "赵云"))
        }
        val resultTv = findViewById<TextView>(R.id.result)
        queryBtn.setOnClickListener {
            // 远程实现是运行在Binder线程池中的，同时客户端线程会挂起，所以如果是耗时操作，应该运行在子线程中
            val studentList = mStudentManager?.studentList
            resultTv.text = "查询结果: $studentList"
        }
        bindService()
    }

    private fun bindService() {
        val intent = Intent(this, RemoteService::class.java)
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE)
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e(TAG, "onServiceConnected: ")
            mStudentManager = IStudentManager.Stub.asInterface(service)
            mStudentManager?.registerListener(mListener)
            // 给binder设置死亡代理
            service?.linkToDeath(mDeathRecipient, 0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected: ")
            mStudentManager = null
        }
    }

    // 远程实现
    private val mListener = object : IStudentListener.Stub() {
        override fun onNewStudent(student: Student?) {
            Log.e(TAG, "onNewStudent: $student")
        }
    }

    private val mDeathRecipient = IBinder.DeathRecipient { onDied() }

    private fun onDied() {
        mStudentManager?.let {
            it.asBinder().unlinkToDeath(mDeathRecipient, 0)
            mStudentManager = null
            // 重新绑定远程服务
            bindService()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mStudentManager?.unregisterListener(mListener)
        unbindService(mConnection)
    }
}