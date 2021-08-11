package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * 钟表View
 * author: zyli44
 * date: 2021/7/29 14:10
 */
class ClockView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private val mBlackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val mRedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private val mRealRect = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        // 方形，宽高相等
        val result = if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY) {
            min(widthSize, heightSize)
        } else {
            val min = min(widthSize, heightSize)
            min(min, DEFAULT_SIZE)
        }
        Log.e(TAG, "onMeasure: $result")
        setMeasuredDimension(result, result)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            Log.e(TAG, "onDraw: $paddingStart  $paddingTop $paddingEnd $paddingBottom")
            // 兼容padding
            mRealRect.left = left + paddingStart
            mRealRect.top = top + paddingTop
            mRealRect.right = right - paddingEnd
            mRealRect.bottom = bottom - paddingBottom
            mBlackPaint.strokeWidth = 3f
            drawRect(mRealRect, mRedPaint)
            // 圆心坐标
            val cx = mRealRect.exactCenterX()
            val cy = mRealRect.exactCenterY()
            // 半径
            val radius =
                min(mRealRect.width(), mRealRect.height()) / 2 - mBlackPaint.strokeWidth / 2
            // 画中心圆
            mBlackPaint.style = Paint.Style.FILL
            drawCircle(cx, cy, 10f, mBlackPaint)
            // 画外圈圆
            mBlackPaint.style = Paint.Style.STROKE
            drawCircle(cx, cy, radius, mBlackPaint)
            // 画刻度
            save()
            for (i in 0..60) {
                val length = if (i % 5 == 0) {
                    // 整点刻度，长、粗刻度
                    mBlackPaint.strokeWidth = 10f
                    15f + mRealRect.top
                } else {
                    // 短、细刻度
                    mBlackPaint.strokeWidth = 5f
                    10f + mRealRect.top
                }
                // 画刻度
                drawLine(cx, mRealRect.top.toFloat(), cx, length, mBlackPaint)
                // 画时间文字，这里导致的结果是数字也会旋转
//                if (i % 5 == 0 && i != 0) {
//                    val text = i / 5
//                    Log.e(TAG, "onDraw: $text")
//                    mBlackPaint.strokeWidth = 1f
//                    mBlackPaint.textSize = 15f
//                    mBlackPaint.textAlign = Paint.Align.CENTER
//                    drawText(text.toString(), width / 2f, 30f, mBlackPaint)
//                }
                // 画布旋转一刻度的角度值
                rotate(360 / 60f, cx, cy)
            }
            restore()
            // 画时间文字，不会翻转
            val textRadius = radius - 50
            mBlackPaint.strokeWidth = 1f
            mBlackPaint.textSize = 15f
            mBlackPaint.textAlign = Paint.Align.CENTER
            for (i in 1..12) {
                val text = i.toString()
                val measureText = mBlackPaint.measureText(text)
                val textX = cx + textRadius * sin(30 * i * Math.PI / 180f)
                val textY = cy - textRadius * cos(30 * i * Math.PI / 180f) + measureText / 2f
                drawText(text, textX.toFloat(), textY.toFloat(), mBlackPaint)
            }

            // 画指针
            val hour = Calendar.getInstance().get(Calendar.HOUR)
            val minute = Calendar.getInstance().get(Calendar.MINUTE)
            val second = Calendar.getInstance().get(Calendar.SECOND)
            Log.e(TAG, "onDraw: $hour  $minute  $second")
            val hourLength = mRealRect.width() / 4f
            val minuteLength = mRealRect.width() / 3f
            val secondLength = mRealRect.width() / 2f - 50
            // 时针
            save()
            val hourDegrees = (hour * (360 / 12) + minute * 30 / 60).toFloat()
            rotate(hourDegrees, cx, cy)
            mRedPaint.strokeWidth = 10f
            drawLine(cx, cy - hourLength, cx, cy + 30, mRedPaint)
            restore()
            // 分针
            save()
            val minuteDegrees = (minute * (360 / 60) + second * 6 / 60).toFloat()
            rotate(minuteDegrees, cx, cy)
            mRedPaint.strokeWidth = 5f
            drawLine(cx, cy - minuteLength, cx, cy + 40, mRedPaint)
            restore()
            // 秒针
            save()
            rotate(second * (360 / 60).toFloat(), cx, cy)
            mRedPaint.strokeWidth = 2f
            drawLine(cx, cy - secondLength, cx, cy + 50, mRedPaint)
            restore()
            // 一秒重绘一次
            postInvalidateDelayed(1000)
        }
    }

    companion object {
        private const val TAG = "ClockView"

        const val DEFAULT_SIZE = 800
    }

}