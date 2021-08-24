package com.lzy.studysource.widget.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.lzy.studysource.R

/**
 * 使用PorterDuffXfermode实现刮刮卡效果
 * author: zyli44
 * date: 2021/8/24 21:02
 */
class ScratchCardView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var mBgBitmap: Bitmap
    private lateinit var mOutBitmap: Bitmap
    private lateinit var mOutCanvas: Canvas
    private val mPath = Path()

    // 后画的图只展示先画的图的范围中
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    init {
        context?.let {
            val typedArray =
                it.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
            val bgResId = typedArray.getResourceId(R.styleable.RoundImageView_bg, 0)
            typedArray.recycle()
            mBgBitmap = BitmapFactory.decodeResource(resources, bgResId)
            mOutBitmap =
                Bitmap.createBitmap(mBgBitmap.width, mBgBitmap.height, Bitmap.Config.ARGB_8888)
            mOutCanvas = Canvas(mOutBitmap)
            // 关闭硬件加速，因为有些xfermode模式不支持硬件加速
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            // 将画笔的透明度设置为0，这是因为PorterDuffXfermode进行图层混合时，并不是简单的只进行图层的计算，也会计算透明度通道的值
            mPaint.alpha = 0
            mPaint.xfermode = mXfermode
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = 50f
            // 可以让笔触和连接处更加圆滑
            mPaint.strokeJoin = Paint.Join.ROUND
            mPaint.strokeCap = Paint.Cap.ROUND
            // 先画一层遮罩
            mOutCanvas.drawColor(Color.GRAY)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    mPath.reset()
                    mPath.moveTo(it.x, it.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    mPath.lineTo(it.x, it.y)
                }
            }
            mOutCanvas.drawPath(mPath, mPaint)
            invalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawBitmap(mBgBitmap, 0f, 0f, null)
            it.drawBitmap(mOutBitmap, 0f, 0f, null)
        }
    }

}