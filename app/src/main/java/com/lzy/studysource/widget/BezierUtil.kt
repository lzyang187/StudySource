package com.lzy.studysource.widget

import android.graphics.PointF


/**
 * 计算贝塞尔曲线上的点坐标
 * author: zyli44
 * date: 2021/7/27 14:53
 */
object BezierUtil {

    /**
     * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param start 起始点
     * @param control 控制点
     * @param end 终止点
     * @return t对应的点
     */
    fun calculateBezierPointForQuadratic(t: Float, start: PointF, control: PointF,
        end: PointF): PointF {
        val point = PointF()
        val temp = 1 - t
        point.x = temp * temp * start.x + 2 * t * temp * control.x + t * t * end.x
        point.y = temp * temp * start.y + 2 * t * temp * control.y + t * t * end.y
        return point
    }

    /**
     * B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param start 起始点
     * @param control1 控制点1
     * @param control2 控制点2
     * @param end 终止点
     * @return t对应的点
     */
    fun calculateBezierPointForCubic(t: Float, start: PointF, control1: PointF, control2: PointF,
        end: PointF): PointF {
        val point = PointF()
        val temp = 1 - t
        point.x =
            start.x * temp * temp * temp + 3 * control1.x * t * temp * temp + 3 * control2.x * t * t * temp + end.x * t * t * t
        point.y =
            start.y * temp * temp * temp + 3 * control1.y * t * temp * temp + 3 * control2.y * t * t * temp + end.y * t * t * t
        return point
    }

}