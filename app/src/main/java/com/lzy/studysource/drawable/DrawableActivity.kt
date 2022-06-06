package com.lzy.studysource.drawable

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.lzy.studysource.R

class DrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        setContentView(R.layout.activity_drawable)
        val iv = findViewById<ImageView>(R.id.iv)
//        iv.setImageLevel(1)
        iv.setImageLevel(8)

        val tv = findViewById<TextView>(R.id.tv)
        tv.background.level = 2

        val custom = findViewById<TextView>(R.id.custom)
        custom.background = CustomDrawable(Color.BLUE)

        val badgeTv = findViewById<TextView>(R.id.badge_tv)
        badgeTv.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                BadgeDrawable.create(this@DrawableActivity).apply {
                    badgeGravity = BadgeDrawable.TOP_END
                    number = 1087
                    maxCharacterCount = 4
                    backgroundColor = Color.RED
                    isVisible = true
                    badgeTextColor = Color.BLUE
                    BadgeUtils.attachBadgeDrawable(this, badgeTv)
                }
                badgeTv.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        val insetTv = findViewById<TextView>(R.id.inset_tv)
        BadgeDrawable.create(this@DrawableActivity).apply {
            badgeGravity = BadgeDrawable.TOP_END
            number = 6
            maxCharacterCount = 4
            backgroundColor = Color.RED
            verticalOffset = 15
            horizontalOffset = -10
            isVisible = true
            BadgeUtils.attachBadgeDrawable(this, insetTv, findViewById(R.id.frame_layout))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG, "onConfigurationChanged:")
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "onConfigurationChanged: 横屏了")
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "onConfigurationChanged: 竖屏了")
        }
    }

    companion object {
        private const val TAG = "DrawableActivity"
    }
}