package com.lzy.studysource.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 三阶贝赛尔曲线
 * author: zyli44
 * date: 2021/7/26 17:19
 */
class ThreeBezierView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private val mBezierPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }
    }

    private val mAuxiliaryPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }

    private val mAuxiliaryTextPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            textSize = 20f
        }
    }

    private val mPath = Path()

    private var mStartPointX = 0f
    private var mStartPointY = 0f
    private var mEndPointX = 0f
    private var mEndPointY = 0f
    private var mControlPointX = 0f
    private var mControlPointY = 0f
    private var mSecondControlPointX = 0f
    private var mSecondControlPointY = 0f

    private var mIsSecondPoint = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mStartPointX = (w / 4).toFloat()
        mStartPointY = (h / 2 - 200).toFloat()
        mEndPointX = (w / 4 * 3).toFloat()
        mEndPointY = mStartPointY
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            // 画辅助点
            drawPoint(mStartPointX, mStartPointY, mAuxiliaryPaint)
            drawPoint(mEndPointX, mEndPointY, mAuxiliaryPaint)
            drawPoint(mControlPointX, mControlPointY, mAuxiliaryPaint)
            drawPoint(mSecondControlPointX, mSecondControlPointY, mAuxiliaryPaint)
            // 画辅助线
            drawLine(mStartPointX, mStartPointY, mControlPointX, mControlPointY, mAuxiliaryPaint)
            drawLine(mControlPointX, mControlPointY, mSecondControlPointX, mSecondControlPointY,
                mAuxiliaryPaint)
            drawLine(mSecondControlPointX, mSecondControlPointY, mEndPointX, mEndPointY,
                mAuxiliaryPaint)
            // 辅助文字
            drawText("起点", mStartPointX, mStartPointY, mAuxiliaryTextPaint)
            drawText("控制点1", mControlPointX, mControlPointY, mAuxiliaryTextPaint)
            drawText("控制点2", mSecondControlPointX, mSecondControlPointY, mAuxiliaryTextPaint)
            drawText("终点", mEndPointX, mEndPointY, mAuxiliaryTextPaint)
            // 曲线
            mPath.reset()
            mPath.moveTo(mStartPointX, mStartPointY)
            mPath.cubicTo(mControlPointX, mControlPointY, mSecondControlPointX,
                mSecondControlPointY, mEndPointX, mEndPointY)
            drawPath(mPath, mBezierPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        event?.apply {
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN -> mIsSecondPoint = true
                MotionEvent.ACTION_MOVE -> {
                    mControlPointX = event.getX(0)
                    mControlPointY = event.getY(0)
                    if (mIsSecondPoint) {
                        mSecondControlPointX = event.getX(1)
                        mSecondControlPointY = event.getY(1)
                    }
                    invalidate()
                }
                MotionEvent.ACTION_POINTER_UP -> mIsSecondPoint = false
            }
        }
        return true
    }

}