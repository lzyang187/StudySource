package com.lzy.studysource.opengles.airhockey.objects

import android.opengl.GLES20
import kotlin.math.cos
import kotlin.math.sin


/**
 * 物体构建器
 * @author: cyli8
 * @date: 2021/11/13 14:42
 */
class ObjectBuilder(
    sizeInVertices: Int,
    private val mVertexData: FloatArray = FloatArray(sizeInVertices * FLOATS_PER_VERTEX)
) {

    interface IDrawCommand {
        fun draw()
    }

    class GeneratedData(val vertexData: FloatArray, val drawList: List<IDrawCommand>) {

    }

    // 记录mVertexData数组中下一个顶点的位置
    private var mOffset = 0
    private val mDrawList = mutableListOf<IDrawCommand>()

    companion object {
        // 一个顶点需要3个浮点数
        private const val FLOATS_PER_VERTEX = 3

        /**
         * 生成冰球，一个圆柱体构成
         */
        fun createPuck(puck: Geometry.Cylinder, numPoints: Int): GeneratedData {
            val size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints)
            val builder = ObjectBuilder(size)
            val puckTop = Geometry.Circle(puck.center.translateY(puck.height / 2f), puck.radius)
            builder.appendCircle(puckTop, numPoints)
            builder.appendOpenCylinder(puck, numPoints)
            return builder.build()
        }

        /**
         * 生成木槌，两个圆柱体构成
         */
        fun createMallet(
            center: Geometry.Point,
            radius: Float,
            height: Float,
            numPoints: Int
        ): GeneratedData {
            val size =
                sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2
            val builder = ObjectBuilder(size)
            // 木槌的底座，占总体高度的25%
            val baseHeight = height * 0.25f
            val baseCircle = Geometry.Circle(center.translateY(-baseHeight), radius)
            val baseCylinder = Geometry.Cylinder(
                baseCircle.center.translateY(-baseHeight / 2f),
                radius,
                baseHeight
            )
            builder.appendCircle(baseCircle, numPoints)
            builder.appendOpenCylinder(baseCylinder, numPoints)
            // 手柄
            val handHeight = height * 0.75f
            val handRadius = radius / 3f
            val handCircle = Geometry.Circle(center.translateY(height * 0.5f), handRadius)
            val handCylinder = Geometry.Cylinder(
                handCircle.center.translateY(-handHeight / 2f),
                handRadius,
                handHeight
            )
            builder.appendCircle(handCircle, numPoints)
            builder.appendOpenCylinder(handCylinder, numPoints)
            return builder.build()

        }

        /**
         * 计算圆柱体顶部顶点的数量
         */
        private fun sizeOfCircleInVertices(numPoints: Int): Int {
            return 1 + (numPoints + 1)
        }

        /**
         * 计算圆柱体侧面顶点的数量
         */
        private fun sizeOfOpenCylinderInVertices(numPoints: Int): Int {
            return (numPoints + 1) * 2
        }
    }


    /**
     * 冰球的顶部
     */
    private fun appendCircle(circle: Geometry.Circle, numPoints: Int) {

        val startVertex = mOffset / FLOATS_PER_VERTEX
        val numVertices = sizeOfCircleInVertices(numPoints)

        // Center point of fan
        mVertexData[mOffset++] = circle.center.x
        mVertexData[mOffset++] = circle.center.y
        mVertexData[mOffset++] = circle.center.z

        // 构建三角形扇，for循环的闭区间是因为起点需要两个三角形扇才能闭合
        for (i in 0..numPoints) {
            val angleInRadius = (i.toFloat() / numPoints.toFloat()) * Math.PI * 2f
            mVertexData[mOffset++] = circle.center.x + circle.radius * cos(angleInRadius).toFloat()
            mVertexData[mOffset++] = circle.center.y
            mVertexData[mOffset++] = circle.center.z + circle.radius * sin(angleInRadius).toFloat()
        }

        mDrawList.add(object : IDrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices)
            }
        })
    }

    /**
     * 圆柱体侧面
     */
    private fun appendOpenCylinder(cylinder: Geometry.Cylinder, numPoints: Int) {
        val startVertex = mOffset / FLOATS_PER_VERTEX
        val numVertices = sizeOfOpenCylinderInVertices(numPoints)
        val yStart = cylinder.center.y - (cylinder.height / 2f)
        val yEnd = cylinder.center.y + (cylinder.height / 2f)

        for (i in 0..numPoints) {
            val angleInRadius = (i.toFloat() / numPoints.toFloat()) * Math.PI * 2f
            val xPosition = cylinder.center.x + cylinder.radius * cos(angleInRadius).toFloat()
            val zPosition = cylinder.center.z + cylinder.radius * sin(angleInRadius).toFloat()
            mVertexData[mOffset++] = xPosition
            mVertexData[mOffset++] = yStart
            mVertexData[mOffset++] = zPosition
            mVertexData[mOffset++] = xPosition
            mVertexData[mOffset++] = yEnd
            mVertexData[mOffset++] = zPosition
        }

        mDrawList.add(object : IDrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices)
            }
        })
    }

    private fun build(): GeneratedData {
        return GeneratedData(mVertexData, mDrawList)
    }


}