package com.lzy.studysource.widget.shader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lzy.studysource.R

/**
 * 图片倒影效果
 * @author: cyli8
 * @date: 2021/8/29 10:48
 */
class ReflectView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private val mBitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.test_filter)
    }
    private var mRefBitmap: Bitmap
    private val mRefPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    }
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    private var mSrcRect: Rect
    private var mDstRect: Rect

    init {
        val matrix = Matrix()
        // 实现图片的垂直翻转，是一个非常有用的技巧
        matrix.setScale(1f, -1f)
        mRefBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.width, mBitmap.height, matrix, true)
        // 渐变渲染器
        mRefPaint.shader = LinearGradient(
            0f,
            mBitmap.height.toFloat(),
            0f,
            (mBitmap.height + mBitmap.height / 2).toFloat(),
            Color.parseColor("#ff000000"),
            Color.parseColor("#00000000"),
            Shader.TileMode.CLAMP
        )
        mSrcRect = Rect(0, 0, mRefBitmap.width, mRefBitmap.height / 2)
        mDstRect =
            Rect(
                0,
                mRefBitmap.height,
                mRefBitmap.width,
                mRefBitmap.height + mRefBitmap.height / 2
            )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawBitmap(mBitmap, 0f, 0f, null)
            // 翻转图片只画倒影高度（本例中是一半高度）就可以了
            it.drawBitmap(mRefBitmap, mSrcRect, mDstRect, null)
            mRefPaint.xfermode = mXfermode
            // 画一个渐变的矩形
            it.drawRect(
                0f,
                mBitmap.height.toFloat(),
                mBitmap.width.toFloat(),
                (mBitmap.height + mBitmap.height / 2).toFloat(),
                mRefPaint
            )
            mRefPaint.setXfermode(null)

        }
    }

}