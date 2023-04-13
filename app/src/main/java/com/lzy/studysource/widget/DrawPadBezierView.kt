package com.lzy.studysource.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlin.math.abs

/**
 * 通过贝赛尔曲线实现圆滑路径
 * author: zyli44
 * date: 2021/7/23 17:26
 */
class DrawPadBezierView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private var mX = 0f
    private var mY = 0f
    private val mOffset = ViewConfiguration.get(context).scaledTouchSlop

    private val mPaint by lazy {
        Paint().apply {
            color = Color.BLUE
            style = Paint.Style.STROKE
            strokeWidth = 2f
            isAntiAlias = true
            isDither = true
        }
    }

    private val mPath by lazy {
        Path()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX = event.x
                mY = event.y
                mPath.reset()
                mPath.moveTo(mX, mY)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(event.x - mX)
                val dy = abs(event.y - mY)
                if (dx >= mOffset || dy >= mOffset) {
                    // 贝塞尔曲线的控制点为起点和终点的中点
                    val cX = (event.x + mX) / 2
                    val cY = (event.y + mY) / 2
                    // 使用贝塞尔曲线变得更加圆滑
                    mPath.quadTo(cX, cY, event.x, event.y)
//                    mPath.lineTo(x1, y1)
                    mX = event.x
                    mY = event.y
                }
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPath(mPath, mPaint)
        }
    }

    companion object {
        private const val TAG = "DrawPadBezierView"
    }

}