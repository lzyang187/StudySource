package com.lzy.studysource.widget.patheffect

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 演示PathEffect的用法
 * @author: cyli8
 * @date: 2021/8/24 21:55
 */
class PathEffectView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0) {}

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()
    private val mEffects = arrayListOf<PathEffect>()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 1f
        // 生成path
        mPath.moveTo(0f, 0f)
        for (i in 0..30) {
            mPath.lineTo(i * 35f, (Math.random() * 100).toFloat())
        }

        // 将拐角处变得圆滑
        mEffects.add(CornerPathEffect(30f))
        // 线段上会产生许多杂点。切断的长度为3，deviation是切断之后线段的偏移量，随机，小于等于deviation
        mEffects.add(DiscretePathEffect(3f, 5f))
        // 绘制虚线，先20像素的实线再10像素的虚线，再5像素实线，再10像素虚线
        mEffects.add(DashPathEffect(floatArrayOf(20f, 10f, 5f, 10f), 0f))
        // 与DashPathEffect类似，不过功能更加强大，可以设置显示点的形状（如方形、圆形）
        val shape = Path()
        shape.addRect(0f, 0f, 8f, 8f, Path.Direction.CCW)
        mEffects.add(PathDashPathEffect(shape, 12f, 0f, PathDashPathEffect.Style.ROTATE))
        // 将两种途径特性组合成一个新效果
        mEffects.add(ComposePathEffect(mEffects[2], mEffects[1]))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            // 先画无效果的
            it.drawPath(mPath, mPaint)
            it.save()
            for (i in 0 until mEffects.size) {
                // 将画布平移
                it.translate(0f, 200f)
                mPaint.pathEffect = mEffects[i]
                it.drawPath(mPath, mPaint)
            }
            it.restore()
        }
    }


}