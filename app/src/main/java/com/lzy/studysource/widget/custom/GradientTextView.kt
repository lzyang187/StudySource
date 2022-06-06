package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

/**
 * 文字闪动的TextView
 * author: zyli44
 * date: 2021/8/15 16:40
 */
class GradientTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    private val mGradientMatrix = Matrix()
    private var mLinearGradient: LinearGradient? = null
    private var mTranslate = 0


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (width > 0) {
            mLinearGradient = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                0f,
                intArrayOf(Color.BLUE, Color.WHITE, Color.BLUE),
                null,
                Shader.TileMode.CLAMP
            )
            // 获取当前绘制TextView的Paint并设置渐变的渲染器
            paint.shader = mLinearGradient
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mTranslate += width / 2
        if (mTranslate > 2 * width) {
            mTranslate = -width
        }
        // 通过矩阵的方式来不断平移渐变效果
        mGradientMatrix.setTranslate(mTranslate.toFloat(), 0f)
        mLinearGradient?.setLocalMatrix(mGradientMatrix)
        postInvalidateDelayed(100)
    }

    companion object {
        private const val TAG = "GradientTextView"
    }

}