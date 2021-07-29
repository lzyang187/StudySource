package com.lzy.studysource.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * author: zyli44
 * date: 2021/7/23 15:16
 */
class PathView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
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
            style = Paint.Style.STROKE
            // 设置画笔的粗细
            strokeWidth = 1f
            // 抗锯齿
            isAntiAlias = true
            // 防抖动
            isDither = true
        }
    }

    private val mPath: Path by lazy {
        // Path的起点默认为坐标为(0,0)
        Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            // 特别注意：建全局Path对象，在onDraw()按需修改；尽量不要在onDraw()方法里new对象
            // 原因：若View频繁刷新，就会频繁创建对象，拖慢刷新速度。
//            Log.e(TAG, "isEmpty = ${mPath.isEmpty}")
            mPath.apply {
                // 当前点（上次操作结束的点）会连接该点
                // 如果没有进行过操作则默认点为坐标原点。
                lineTo(400f, 500f)
                // 设置当前点位置
                // 后面的路径会从该点开始画
                // 不会影响之前的操作
                moveTo(300f, 300f)

                // 将当前点移动到(300, 300)
                // 会影响之前的操作
                // 但不将此设置为新起点
//                setLastPoint(300f, 300f)
                lineTo(900f, 800f)
                lineTo(200f, 700f)
                // 闭合路径，即连接当前点和起点
                // 即连接(200,700)与起点2(300, 300)
                // 注:此时起点已经进行变换
                close()
            }
//            it.drawPath(mPath, mPaint)
            // 重置Path
            mPath.reset()
            Log.e(TAG, "reset isEmpty = ${mPath.isEmpty}")

            mPath.apply {
                val rectF = RectF(10f, 10f, 200f, 100f)
//            Log.e(TAG, "isRect: ${mPath.isRect(rectF)}")
                // 加入矩形路径
                // 路径起点变为矩形的左上角顶点
                addRect(rectF, Path.Direction.CW)
//            Log.e(TAG, "isEmpty = ${mPath.isEmpty}")
                Log.e(TAG, "isRect: ${mPath.isRect(rectF)}")
                lineTo(300f, 300f)
                // 加入圆形路径
                // 起点：x轴正方向的0度
                // 其中参数dir：指定绘制时是顺时针还是逆时针:CW为顺时针，  CCW为逆时针
                // 路径起点变为圆在X轴正方向最大的点
                addCircle(300f, 300f, 100f, Path.Direction.CCW)
                lineTo(500f, 500f)
                addRoundRect(500f, 500f, 800f, 700f, 10f, 10f, Path.Direction.CCW)
                lineTo(10f, 900f)
            }
            it.drawPath(mPath, mPaint)

        }
    }

    companion object {
        private const val TAG = "PathView"
    }

}