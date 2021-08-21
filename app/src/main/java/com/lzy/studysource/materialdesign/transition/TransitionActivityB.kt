package com.lzy.studysource.materialdesign.transition

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

/**
 * Activity过渡动画
 * author: zyli44
 * date: 2021/8/21 19:44
 */
class TransitionActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        setContentView(R.layout.activity_transition_b)
        // 获取参数
        when (intent?.extras?.getInt("flag")) {
            // 设置不同的进入TransitionActivityB的动画效果和退出效果
            0 -> {
                window.enterTransition = Explode()
                window.exitTransition = Explode()
            }
            1 -> {
                window.enterTransition = Slide()
                window.exitTransition = Slide()
            }
            2 -> {
                window.enterTransition = Fade()
                window.exitTransition = Fade()
            }
        }
    }
}