package com.lzy.studysource.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator


/**
 * 贝塞尔路径动画
 * author: zyli44
 * date: 2021/7/27 14:36
 */
class PathBezierView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr), View.OnClickListener {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null)

    init {
        setOnClickListener(this)
    }

    private var mStartPointX = 100f
    private var mStartPointY = 100f
    private var mEndPointX = 600f
    private var mEndPointY = 600f
    private var mControlPointX = 500f
    private var mControlPointY = 0f
    private var mMovePointX = mStartPointX
    private var mMovePointY = mStartPointY

    private val mPath = Path()

    private val mPathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.BLACK
    }

    private val mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            // 画起点和终点圆
            drawCircle(mStartPointX, mStartPointY, 30f, mCirclePaint)
            drawCircle(mEndPointX, mEndPointY, 30f, mCirclePaint)
            // 画辅助线
            mPath.reset()
            mPath.moveTo(mStartPointX, mStartPointY)
            mPath.quadTo(mControlPointX, mControlPointY, mEndPointX, mEndPointY)
            drawPath(mPath, mPathPaint)
            // 运动的圆
            drawCircle(mMovePointX, mMovePointY, 30f, mCirclePaint)
        }
    }

    override fun onClick(v: View?) {
        val bezierEvaluator = BezierEvaluator(PointF(mControlPointX, mControlPointY))
        val anim = ValueAnimator.ofObject(bezierEvaluator, PointF(mStartPointX, mStartPointY),
            PointF(mEndPointX, mEndPointY))
        anim.duration = 600
        anim.addUpdateListener { valueAnimator ->
            val point = valueAnimator.animatedValue as PointF
            mMovePointX = point.x
            mMovePointY = point.y
            invalidate()
        }
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.start()
    }


}