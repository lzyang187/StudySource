package com.lzy.studysource.opengles.airhockey.objects

import com.lzy.studysource.opengles.airhockey.data.VertexArray
import com.lzy.studysource.opengles.airhockey.programs.ColorShaderProgramWithoutColor

/**
 * 更好的木槌
 *
 * @author: cyli8
 * @date: 2021/11/15 22:42
 */
class MalletBatter(
    val radius: Float,
    val height: Float,
    numPointsAroundMallet: Int
) {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    private var mVertexArray: VertexArray
    private var mDrawList: List<ObjectBuilder.IDrawCommand>

    init {
        val generatedData = ObjectBuilder.createMallet(
            Geometry.Point(0f, 0f, 0f), radius, height,
            numPointsAroundMallet
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