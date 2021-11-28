package com.lzy.studysource.opengles.airhockey.objects

import com.lzy.studysource.opengles.airhockey.data.VertexArray
import com.lzy.studysource.opengles.airhockey.programs.ColorShaderProgramWithoutColor

/**
 * 冰球
 *
 * @author: cyli8
 * @date: 2021/11/15 22:42
 */
class Puck(val radius: Float, val height: Float, numPointsAroundPuck: Int) {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    private lateinit var mVertexArray: VertexArray
    private lateinit var mDrawList: List<ObjectBuilder.IDrawCommand>

    init {
        val generatedData = ObjectBuilder.createPuck(
            Geometry.Cylinder(Geometry.Point(0f, 0f, 0f), radius, height),
            numPointsAroundPuck
        )
        mVertexArray = VertexArray(generatedData.vertexData)
        mDrawList = generatedData.drawList
    }

    fun bindData(colorShaderProgram: ColorShaderProgramWithoutColor) {
        mVertexArray.setVertexAttributePointer(
            0, colorShaderProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, 0
        )
    }

    fun draw() {
        mDrawList.forEach {
            it.draw()
        }
    }
}