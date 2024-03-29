package com.lzy.studysource.widget.shader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lzy.studysource.R

/**
 * 使用BitmapShader实现圆角图形
 * author: zyli44
 * date: 2021/8/24 22:50
 */
class ShaderRoundImageView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var mSrcBitmap: Bitmap

    init {
        context?.let {
            val typedArray =
                it.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
            val bgResId = typedArray.getResourceId(R.styleable.RoundImageView_bg, 0)
            typedArray.recycle()
            mSrcBitmap = BitmapFactory.decodeResource(resources, bgResId)
            // BitmapShader的用法
//            val bitmapShader =
//                BitmapShader(mSrcBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
//            val bitmapShader =
//                BitmapShader(mSrcBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR)
//            mPaint.setShader(bitmapShader)

            // LinearGradient的用法。是一个从（0，0）到（100，100）由蓝渐变到黄的效果
            val linearGradient =
                LinearGradient(
                    0f, 0f, 100f, 100f,
                    Color.BLUE, Color.YELLOW, Shader.TileMode.CLAMP
                )
            mPaint.setShader(linearGradient)

        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
//            it.drawCircle(width / 2f, height / 2f, width / 2f, mPaint)
            it.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        }
    }

}