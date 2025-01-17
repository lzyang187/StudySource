package com.lzy.studysource.backcallback

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

/**
 * 预测性返回手势新特性的适配
 */
class BackCallbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back_callback)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e(TAG, "handleOnBackPressed: ")
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
        // 是否拦截返回事件
        callback.isEnabled = true
    }

    companion object {
        private const val TAG = "BackCallbackActivity"
    }

}