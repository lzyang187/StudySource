package com.lzy.studysource.window

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class WindowManagerActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_manager)


        val btn = Button(this)
        btn.text = "添加的按钮"
        btn.setBackgroundColor(Color.BLUE)
        val windowParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSPARENT
        )
        windowParams.gravity = Gravity.START or Gravity.TOP
        windowParams.x = 500
        windowParams.y = 500
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // 添加view
        windowManager.addView(btn, windowParams)

        btn.setOnTouchListener { v, event ->
            event?.let {
                if (it.action == MotionEvent.ACTION_MOVE) {
                    windowParams.x = it.rawX.toInt()
                    windowParams.y = it.rawY.toInt()
                    // 更新view
                    windowManager.updateViewLayout(btn, windowParams)
                } else if (it.action == MotionEvent.ACTION_UP) {
                    // 移除view
                    windowManager.removeView(btn)
                }
            }
            true
        }
    }

}