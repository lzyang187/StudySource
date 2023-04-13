package com.lzy.studysource.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * 手势的使用
 * @author: cyli8
 * @date: 2021/8/5 13:13
 */
class GestureView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    companion object {
        private const val TAG = "GestureView"
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private val mGestureDetector = GestureDetector(context, this)

    init {
        // 解决长按屏幕后无法拖动的现象
        mGestureDetector.setIsLongpressEnabled(true)
        mGestureDetector.setOnDoubleTapListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

    /**
     * 手指轻轻触摸屏幕的一瞬间
     */
    override fun onDown(e: MotionEvent): Boolean {
        Log.e(TAG, "onDown: ")
        return true
    }

    /**
     * 手指轻轻触摸屏幕，尚未松开或拖动
     */
    override fun onShowPress(e: MotionEvent) {
        Log.e(TAG, "onShowPress: ")
    }

    /**
     * 手指松开
     */
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.e(TAG, "onSingleTapUp: ")
        return false
    }

    /**
     * 手指拖动
     */
    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.e(TAG, "onScroll 计算: ${e1.x - e2.x}  ${e1.y - e2.y}")
        Log.e(TAG, "onScroll: $distanceX   $distanceY")
        return false
    }

    /**
     * 手指长按屏幕不放
     */
    override fun onLongPress(e: MotionEvent) {
        Log.e(TAG, "onLongPress: ")
    }

    /**
     * 手指快速滑动后松开
     */
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.e(TAG, "onFling: $velocityX  $velocityY")
        return false
    }

    /**
     * 严格的单击行为，如果触发了此回调，那么它后面不可能再紧跟着另一个单击行为。
     * 即这只可能是单击，而不可能是双击中的一次单击
     */
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.e(TAG, "onSingleTapConfirmed: ")
        return false
    }

    /**
     * 双击，不可能和onSingleTapConfirmed共存
     */
    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.e(TAG, "onDoubleTap: ")
        return false
    }

    /**
     * 表示发生了双击行为，在双击的期间，都会触发此回调
     */
    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.e(TAG, "onDoubleTapEvent: ")
        return false
    }

}