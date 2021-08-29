package com.lzy.studysource.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.sin

/**
 * 用SurfaceView绘制正弦曲线
 * @author: cyli8
 * @date: 2021/8/29 12:32
 */
class SinSurfaceView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    SurfaceView(context, attrs, defStyleAttr), Runnable, SurfaceHolder.Callback {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    private var mIsDrawing = false
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mCanvas: Canvas? = null
    private var mX = 0
    private var mY = 0
    private val mPath = Path()

    init {
        holder.addCallback(this)
        mPaint.color = Color.GRAY
        mPaint.style = Paint.Style.STROKE
    }

    override fun run() {
        while (mIsDrawing) {
            // 开启循环，不停地进行绘制
            doDraw()
            mX += 1
            mY = (100 * sin(mX * 2 * Math.PI / 180) + 400).toInt()
            mPath.lineTo(mX.toFloat(), mY.toFloat())
        }
    }

    private fun doDraw() {
        try {
            // 获得当前画布，还是继续上次的画布，而不是新对象
            mCanvas = holder.lockCanvas()
            // 画白色背景
            mCanvas?.drawColor(Color.WHITE)
            mCanvas?.drawPath(mPath, mPaint)
        } catch (e: Exception) {
        } finally {
            // 对画布内容进行提交
            holder.unlockCanvasAndPost(mCanvas)
        }

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mIsDrawing = true
        // 开启子线程进行绘制
        Thread(this).start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mIsDrawing = false
    }

}