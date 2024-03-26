package com.lzy.studysource.ipcdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList

class RemoteService : Service() {

    private val mBookList = CopyOnWriteArrayList<Student>()
    private val mRemoteCallbackList = RemoteCallbackList<IStudentListener>()

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    // 远程实现
    private val mBinder = object : IStudentManager.Stub() {
        override fun addStudent(student: Student?) {
            Log.e("zhaoyang", "addStudent: ")
            mBookList.add(student)
            // 通知变化
            val size = mRemoteCallbackList.beginBroadcast()
            for (i in 0 until size) {
                // 远程实现是运行在Binder线程池中的，同时客户端线程会挂起，所以如果是耗时操作，应该运行在子线程中
                mRemoteCallbackList.getBroadcastItem(i).onNewStudent(student)
            }

            mRemoteCallbackList.finishBroadcast()
        }

        override fun getStudentList(): MutableList<Student> {
            // 模拟查询耗时
//            Thread.sleep(10000)
            return mBookList
        }

        override fun registerListener(listener: IStudentListener?) {
            mRemoteCallbackList.register(listener)
        }

        override fun unregisterListener(listener: IStudentListener?) {
            mRemoteCallbackList.unregister(listener)
        }
    }

}