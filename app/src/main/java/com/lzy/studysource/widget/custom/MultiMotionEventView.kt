package com.lzy.studysource.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * 1、多点触控事件序列：
 * ACTION_DOWN：第一个手指按下（之前没有任何手指触摸到View）
 * ACTION_MOVE：有手指发生移动
 * ACTION_MOVE
 * ACTION_POINTER_DOWN：额外手指按下（按下之前已经有别的手指触摸到View）
 * ACTION_MOVE
 * ACTION_MOVE
 * ACTION_POINTER_UP：有手指抬起，但不是最后一个（抬起之后，仍然还有别的手指在触摸着View）
 * ACTION_MOVE
 * ACTION_UP：最后一个手指抬起（抬起之后没有任何手指触摸到View，这个手指未必是ACTION_DOWN的那个手指）
 *
 * 2、index 它并不是固定的，它根据事件序列的改变而改变。
 * 3、id 在整个事件序列中它都是固定不变的，通过它我们就可以获取手指当前的 index，继而通过 index 再调用 getX(index) 和 getY(index) 获取到坐标点。
 * 4、getActionIndex() 这个 API 比较特殊，它在多点触控的场景只适用于 MotionEvent.ACTION_POINTER_DOWN 和 MotionEvent.ACTION_POINTER_UP 事件回调时使用
 * 那为什么不能在 MotionEvent.ACTION_MOVE 获取调用？
 * 当多个手指触摸的时候，其实多个手指是在同时移动的（手指的轻微移动都会回调 MotionEvent.ACTION_MOVE)。
 * 我们上面说到 pointer 的索引 index 是会根据事件序列的改变而改变的，也就是说导致 MotionEvent.ACTION_MOVE 的手指索引是不断切换的。
 * 所以在 MotionEvent.ACTION_MOVE 并不方便获取正在导致那个事件的手指，也是没有意义的，在 MotionEvent.ACTION_MOVE 调用 MotionEvent.getActionIndex() 总是返回 0，该值没有任何意义不代表哪个手指的索引。
 */
class MultiMotionEventView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        Log.d(TAG, "onTouchEvent : ACTION_MASK = ${MotionEvent.ACTION_MASK}")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        Log.e(TAG, "onTouchEvent: action = $action")
        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val pointerIndex = event.actionIndex
                Log.e(TAG, "onTouchEvent: pointerIndex = $pointerIndex")
                val pointerId = event.getPointerId(pointerIndex)
                Log.d(TAG, "Pointer $pointerId down")
            }

            MotionEvent.ACTION_MOVE -> {
                var i = 0
                while (i < event.pointerCount) {
                    val pointerId = event.getPointerId(i)
                    val x = event.getX(i)
                    val y = event.getY(i)
                    Log.d(TAG, "Pointer $pointerId move to ($x, $y)")
                    i++
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = event.actionIndex
                Log.e(TAG, "onTouchEvent: pointerIndex = $pointerIndex")
                val pointerId = event.getPointerId(pointerIndex)
                Log.d(TAG, "Pointer $pointerId up")
            }
        }

        return true
    }

    companion object {
        private const val TAG = "MotionEventView"
    }

}