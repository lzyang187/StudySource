package com.lzy.studysource.animation.valueanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R


/**
 * 属性动画的代码
 */
class ValueAnimatorActivity : AppCompatActivity() {

    private lateinit var mBtn: Button
    private lateinit var mSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animator)

        mBtn = findViewById(R.id.btn)
        mSeekBar = findViewById(R.id.seek_bar)
        fractionDemo()
    }

    private fun fractionDemo() {
        // 单个属性动画
        val animator = ObjectAnimator.ofInt(
            mBtn,
            "backgroundColor",
            0xFFFF0000.toInt(),
            0XFF0000FF.toInt()
        )
        animator.duration = 3000
        // 设置插值器
        animator.interpolator = LinearInterpolator()
        // 设置估值器
        animator.setEvaluator(ArgbEvaluator())

        // 设置动画数值更新监听器
        animator.addUpdateListener { animation ->
            val fraction = animation.animatedFraction
            Log.e(TAG, "onAnimationUpdate fraction: $fraction")
        }
        // 设置 SeekBar 的监听器
        mSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // 当用户拖动 SeekBar 时，设置动画的当前进度为 SeekBar 的进度
                    val fraction = progress.toFloat() / seekBar.max
                    animator.setCurrentFraction(fraction)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // 暂时不需要处理
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // 暂时不需要处理
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // 单个属性动画
            val animator = ObjectAnimator.ofInt(
                mBtn,
                "backgroundColor",
                0xFFFF0000.toInt(),
                0XFF0000FF.toInt()
            )
            animator.duration = 3000
            // 设置插值器
//            animator.interpolator = AccelerateInterpolator()
//            animator.interpolator = DecelerateInterpolator()
            animator.interpolator = AccelerateDecelerateInterpolator()
            // 设置估值器
            animator.setEvaluator(ArgbEvaluator())
            animator.repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.REVERSE
//            animator.start()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationCancel(animation: Animator) {
                    super.onAnimationCancel(animation)
                    Log.e(TAG, "onAnimationCancel: ")
                }

                override fun onAnimationStart(animation: Animator, isReverse: Boolean) {
                    super.onAnimationStart(animation, isReverse)
                    Log.e(TAG, "onAnimationStart: $isReverse")
                }

                override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                    super.onAnimationEnd(animation, isReverse)
                    Log.e(TAG, "onAnimationEnd: $isReverse")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    super.onAnimationRepeat(animation)
                    Log.e(TAG, "onAnimationRepeat: ")
                }

                override fun onAnimationPause(animation: Animator) {
                    super.onAnimationPause(animation)
                    Log.e(TAG, "onAnimationPause: ")
                }
            })
            animator.addUpdateListener { Log.i(TAG, "onAnimationUpdate: ") }

            // 属性动画集合
            val set = AnimatorSet()
            set.playTogether(
                animator,
                ObjectAnimator.ofFloat(mBtn, "rotationX", 0f, 360f),
                ObjectAnimator.ofFloat(mBtn, "rotationY", 0f, 360f),
                ObjectAnimator.ofFloat(mBtn, "alpha", 1f, 0.25f, 1f)
            )
            set.duration = 5000
//            set.start()


            mBtn.postDelayed({
//                set.resume()
//                set.pause()
                // 10s后取消动画
                set.cancel()
            }, 10000)

            // 对View的宽度做动画
            val viewWrapper = ViewWrapper(mBtn)
            ObjectAnimator.ofInt(viewWrapper, "width", 0, 500).also {
                it.duration = 3000
                it.start()
            }

        }
    }

    companion object {
        private const val TAG = "ValueAnimatorActivity"
    }
}