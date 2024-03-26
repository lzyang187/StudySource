package com.lzy.studysource.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * 多点触控的示例
 */
class MotionEventView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
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

            MotionEvent.ACTION_CANCEL -> {
                val pointerIndex = event.actionIndex
                Log.e(TAG, "onTouchEvent: pointerIndex = $pointerIndex")
                val pointerId = event.getPointerId(pointerIndex)
                Log.d(TAG, "Pointer $pointerId cancel")
            }
        }

        return true
    }

    companion object {
        private const val TAG = "MotionEventView"
    }

}