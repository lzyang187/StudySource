package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.math.min

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
            Log.e(TAG, "onDraw: $width  $measuredWidth $height $measuredHeight")
            mBlackPaint.strokeWidth = 3f
            // 画中心圆
            mBlackPaint.style = Paint.Style.FILL
            drawCircle(width / 2f, height / 2f, 10f, mBlackPaint)
            // 画外圈圆
            mBlackPaint.style = Paint.Style.STROKE
            drawCircle(width / 2f, height / 2f, width / 2f - mBlackPaint.strokeWidth / 2,
                mBlackPaint)
            // 画刻度
            save()
            for (i in 0..60) {
                val length = if (i % 5 == 0) {
                    // 整点刻度，长、粗刻度
                    mBlackPaint.strokeWidth = 10f
                    15f
                } else {
                    // 短、细刻度
                    mBlackPaint.strokeWidth = 5f
                    10f
                }
                // 画刻度
                drawLine(width / 2f, 0f, width / 2f, length, mBlackPaint)
                // 画时间文字，这里导致的结果是数字也会旋转
                if (i % 5 == 0 && i != 0) {
                    val text = i / 5
                    Log.e(TAG, "onDraw: $text")
                    mBlackPaint.strokeWidth = 1f
                    mBlackPaint.textSize = 15f
                    mBlackPaint.textAlign = Paint.Align.CENTER
                    drawText(text.toString(), width / 2f, 30f, mBlackPaint)
                }
                // 画布旋转一刻度的角度值
                rotate(360 / 60f, width / 2f, height / 2f)
            }
            restore()
            // 画指针
            val hour = Calendar.getInstance().get(Calendar.HOUR)
            val minute = Calendar.getInstance().get(Calendar.MINUTE)
            val second = Calendar.getInstance().get(Calendar.SECOND)
            Log.e(TAG, "onDraw: $hour  $minute  $second")
            val hourLength = width / 4f
            val minuteLength = width / 3f
            val secondLength = width / 2f - 50
            // 时针
            save()
            rotate(hour * (360 / 12).toFloat(), width / 2f, height / 2f)
            mRedPaint.strokeWidth = 10f
            drawLine(width / 2f, height / 2f - hourLength, width / 2f, height / 2f + 30, mRedPaint)
            restore()
            // 分针
            save()
            rotate(minute * (360 / 60).toFloat(), width / 2f, height / 2f)
            mRedPaint.strokeWidth = 5f
            drawLine(width / 2f, height / 2f - minuteLength, width / 2f, height / 2f + 40,
                mRedPaint)
            restore()
            // 秒针
            save()
            rotate(second * (360 / 60).toFloat(), width / 2f, height / 2f)
            mRedPaint.strokeWidth = 2f
            drawLine(width / 2f, height / 2f - secondLength, width / 2f, height / 2f + 50,
                mRedPaint)
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