package com.lzy.studysource.ipcdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
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
            mBookList.add(student)
            // 通知变化
            val size = mRemoteCallbackList.beginBroadcast()
            for (i in 0 until size) {
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