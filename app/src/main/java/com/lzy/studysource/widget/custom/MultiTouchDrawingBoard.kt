package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import androidx.core.util.forEach

/**
 * 多点触控
 * 各自为战型：各个 pointer 做不同的事，互不影响。典型：支持多画笔的画板应用。
 * 实现方式：在每个 MotionEvent.ACTION_DOWN、MotionEvent.ACTION_POINTER_DOWN 中记录下每个 pointer的 id，
 * 在 MotionEvent.ACTION_MOVE 中使用 id 对它们进行跟踪。
 */
class MultiTouchDrawingBoard(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val mPaint: Paint by lazy {
        Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 1f
            isAntiAlias = true
            isDither = true
        }
    }
    private val mSparseArray = SparseArray<Path>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val actionMasked = event.actionMasked
        when (actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                val path = Path()
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                mSparseArray[pointerId] = path
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    val pointerId = event.getPointerId(i)
                    val path = mSparseArray[pointerId]
                    if (path != null) {
                        path.lineTo(event.getX(i), event.getY(i))
                    } else {
                        Log.e(TAG, "onTouchEvent: path == null pointerId = $pointerId")
                    }
                }
                invalidate()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                mSparseArray.remove(pointerId)
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mSparseArray.forEach { key, value ->
            canvas.drawPath(value, mPaint)
        }
    }

    companion object {
        private const val TAG = "MultiTouchDrawingBoard"
    }

}