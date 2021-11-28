package com.lzy.studysource.opengles.airhockey.objects

/**
 * 几何图形
 * @author: cyli8
 * @date: 2021/11/13 14:36
 */
class Geometry {

    /**
     * 表示三维场景中的一个点
     */
    class Point(val x: Float, val y: Float, val z: Float) {
        /**
         * 沿着Y轴平移
         */
        fun translateY(distance: Float): Point {
            return Point(x, y + distance, z)
        }
    }

    /**
     * 圆
     */
    class Circle(val center: Point, val radius: Float) {
        /**
         * 缩放圆的半径
         */
        fun scale(scale: Float): Circle {
            return Circle(center, radius * scale)
        }
    }

    /**
     * 圆柱体
     */
    class Cylinder(
        val center: Point,//圆柱体的中心
        val radius: Float, val height: Float
    ) {

    }


}