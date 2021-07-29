package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.lzy.studysource.R

/**
 * attrs的声明与引入
 * author: zyli44
 * date: 2021/7/28 14:21
 */
class CustomView1(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private var mText = ""
    private var mColor = Color.TRANSPARENT
    private var mSize = 0f
    private var mBackground = 0

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        context?.let {
            val typedArray = it.obtainStyledAttributes(attrs, R.styleable.CustomView1)
            mText = typedArray.getString(R.styleable.CustomView1_text) ?: ""
            mSize = typedArray.getDimensionPixelSize(R.styleable.CustomView1_size, 0).toFloat()
            mColor = typedArray.getColor(R.styleable.CustomView1_color, Color.TRANSPARENT)
            val focus = typedArray.getBoolean(R.styleable.CustomView1_focus, false)
            Log.e(TAG, "focus: $focus")
            mBackground = typedArray.getResourceId(R.styleable.CustomView1_background, 0)
            val count = typedArray.getInt(R.styleable.CustomView1_count, 0)
            Log.e(TAG, "count: $count")
            val alpha = typedArray.getFloat(R.styleable.CustomView1_alpha, 0f)
            Log.e(TAG, "alpha: $alpha")
            val pivot = typedArray.getFraction(R.styleable.CustomView1_pivot, 1, 1, 0f)
            Log.e(TAG, "pivot: $pivot")
            val orientation = typedArray.getInt(R.styleable.CustomView1_orientation, 0)
            Log.e(TAG, "orientation: $orientation")
            // 别忘记回收
            typedArray.recycle()

            mPaint.color = mColor
            mPaint.textSize = mSize
        }
    }

    private val mRect = Rect()

    /**
     * 当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        mPaint.getTextBounds(mText, 0, mText.length, mRect)

        val width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            val textWidth = mRect.width()
            paddingLeft + textWidth + paddingRight
        }

        val height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            val textHeight = mRect.height()
            paddingTop + textHeight + paddingBottom
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawColor(Color.YELLOW)
            drawText(mText, (width / 2 - mRect.width() / 2).toFloat(),
                (height / 2 + mRect.height() / 2).toFloat(), mPaint)
        }
    }

    companion object {
        private const val TAG = "CustomView1"
    }

}