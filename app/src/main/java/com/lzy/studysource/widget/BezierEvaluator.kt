package com.lzy.studysource.widget

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * 改变运动点坐标的关键evaluator
 * author: zyli44
 * date: 2021/7/27 14:50
 */
class BezierEvaluator(private val mControlPoint: PointF) : TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF?, endValue: PointF?): PointF {
        return BezierUtil.calculateBezierPointForQuadratic(fraction, startValue!!, mControlPoint,
            endValue!!)
    }

}