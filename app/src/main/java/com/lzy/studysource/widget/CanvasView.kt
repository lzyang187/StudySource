package com.lzy.studysource.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.lzy.studysource.R

/**
 * author: zyli44
 * date: 2021/7/22 14:33
 */
class CanvasView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private val mPaint: Paint by lazy {
        Paint().apply {
            /**
             * 通用设置
             */
            // 设置画笔颜色
            color = Color.BLUE
            // 设置画笔模式
//            style = Paint.Style.FILL
            style = Paint.Style.STROKE
//            style = Paint.Style.FILL_AND_STROKE
            // 设置画笔的粗细
            strokeWidth = 5f
            // 设置透明度
            alpha = 255
            // 抗锯齿
            isAntiAlias = true
            // 防抖动
            isDither = true
            // 设置画笔的a,r,p,g值
            setARGB(255, 0, 0, 255)

            /**
             * 画笔在字体相关的设置
             */
            // 设置字体大小
            textSize = 30f
            // 设置对齐方式
            textAlign = Paint.Align.LEFT
//            textAlign = Paint.Align.RIGHT
//            textAlign = Paint.Align.CENTER
            // 设置文本的下划线
//            isUnderlineText = true
            // 设置文本的删除线
//            isStrikeThruText = true
            // 设置文本粗体
//            isFakeBoldText = true
            // 设置文本斜体
//            textSkewX = -0.25f
            // 设置文本阴影
//            setShadowLayer(5f, 2f, 2f, Color.GRAY)
        }
    }

    private val mFillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val mPath = Path()

    private val mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.test_filter)

    init {
        /**
         * Canvas对象的创建
         */
        // 利用空构造方法直接创建对象
        val canvas1 = Canvas()
        // 通过传入装载画布Bitmap对象创建Canvas对象
//        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val canvas2 = Canvas()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            // 绘制画布底色
            drawColor(Color.WHITE)
            // 绘制点
            drawPoint(10f, 10f, mPaint)
            val pts = floatArrayOf(20f, 20f, 20f, 40f)
            drawPoints(pts, mPaint)
            // 绘制线
            drawLine(40f, 20f, 80f, 30f, mPaint)
            drawLines(floatArrayOf(50f, 30f, 80f, 40f, 90f, 20f, 110f, 20f), mPaint)
            // 绘制矩形
            drawRect(120f, 20f, 150f, 30f, mPaint)
//            val rectF = RectF(160f, 20f, 200f, 40f)
//            drawRect(rectF, mPaint)
            // 圆角矩形的角是椭圆的圆弧，rx 和 ry实际上分别是椭圆的x方向半径和y方向半径
            mPaint.style = Paint.Style.STROKE
            drawRoundRect(160f, 20f, 260f, 80f, 10f, 20f, mPaint)
            // 画椭圆
            drawOval(160f, 20f, 260f, 80f, mFillPaint)
            // 画圆
            drawCircle(350f, 100f, 50f, mPaint)
            // 画圆弧：通过圆弧角度的起始位置和扫过的角度确定圆弧
            val arcRectF = RectF(500f, 20f, 800f, 320f)
            drawRect(arcRectF, mPaint)
            mPaint.color = Color.BLUE
            drawArc(arcRectF, 0f, 90f, false, mFillPaint)
            mPaint.color = Color.RED
            drawArc(arcRectF, 90f, 180f, true, mFillPaint)

            val text = "efghijklmn？"
            mPaint.strokeWidth = 1f
            drawLine(10f, 500f, 800f, 500f, mPaint)
            // 从起始位置画文字
//            drawText(text, 10f, 500f, mPaint)
            drawText(text, 1, 4, 10f, 500f, mPaint)
            // 指定路径绘制文字
            mPath.reset()
            mPath.moveTo(10f, 600f)
            mPath.quadTo(200f, 600f, 400f, 800f)
            drawPath(mPath, mPaint)
            drawTextOnPath(text, mPath, 50f, 0f, mPaint)
            // 绘制图片
//            drawBitmap(mBitmap, 900f, 20f, mPaint)
//            drawBitmap(mBitmap, Matrix(), mPaint)
            // 指定图片绘制区域和显示区域（这里仅绘制图片的二分之一）
            drawBitmap(mBitmap, Rect(0, 0, mBitmap.width / 2, mBitmap.height),
                Rect(900, 20, 1000, 220), mPaint)


            // 画布操作
            // 位移：基于当前位置移动，而不是每次都是基于屏幕左上角的(0,0)点移动
            translate(100f, 100f)
            // 缩放：把形状先画到画布，然后再缩小/放大。所以当放大倍数很大时，会有明显锯齿
            scale(2f, 2f, 100f, 0f)
            // 旋转：角度增加方向为顺时针（区别于数学坐标系）
            rotate(90f, 30f, 30f)
            // 倾斜：将画布在x方向倾斜a角度、在y方向倾斜b角度。参数 sx = tan a，参数 sy = tan b
            skew(1f, 1f)

            // 画布快照
            Log.e(TAG, "saveCount1: $saveCount")
            save()
            Log.e(TAG, "saveCount2: $saveCount")
            // 画布剪裁
            clipRect(100, 100, 800, 800)
            drawColor(Color.GRAY)
            drawText("剪裁后", 100f, 100f, mPaint)
            restore()
            Log.e(TAG, "saveCount3: $saveCount")

        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // 在layout过程中onLayout()去获取最终的宽 / 高
        Log.e(TAG, "onLayout: $width $measuredWidth $height $measuredHeight")
    }

    companion object {
        private const val TAG = "CanvasView"
    }

}