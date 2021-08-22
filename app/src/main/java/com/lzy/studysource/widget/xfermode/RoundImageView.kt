package com.lzy.studysource.widget.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lzy.studysource.R

/**
 * 使用PorterDuffXfermode实现圆角图形
 * author: zyli44
 * date: 2021/8/22 16:50
 */
class RoundImageView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var mSrcBitmap: Bitmap
    private lateinit var mOutBitmap: Bitmap
    private lateinit var mOutCanvas: Canvas

    // 后画的图只展示先画的图的范围中
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    init {
        context?.let {
            val typedArray =
                it.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
            val bgResId = typedArray.getResourceId(R.styleable.RoundImageView_bg, 0)
            typedArray.recycle()
            mSrcBitmap = BitmapFactory.decodeResource(resources, bgResId)
            mOutBitmap =
                Bitmap.createBitmap(mSrcBitmap.width, mSrcBitmap.height, Bitmap.Config.ARGB_8888)
            mOutCanvas = Canvas(mOutBitmap)
            // 关闭硬件加速，因为使用了xfermode导致黑边
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
//            mOutCanvas.drawRoundRect(
//                0f, 0f,
//                mOutBitmap.width.toFloat(), mOutBitmap.width.toFloat(), 80f, 80f, mPaint
//            )
            mOutCanvas.drawCircle(
                mOutBitmap.width / 2f,
                mOutBitmap.height / 2f,
                mOutBitmap.width / 2f,
                mPaint
            )
            mPaint.xfermode = mXfermode
            mOutCanvas.drawBitmap(mSrcBitmap, 0f, 0f, mPaint)
            mPaint.xfermode = null
            it.drawBitmap(mOutBitmap, 0f, 0f, mPaint)

        }
    }

}