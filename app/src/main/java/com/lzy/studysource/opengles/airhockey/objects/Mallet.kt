package com.lzy.studysource.opengles.airhockey.objects

import android.opengl.GLES20
import com.lzy.studysource.opengles.airhockey.data.Constants
import com.lzy.studysource.opengles.airhockey.data.VertexArray
import com.lzy.studysource.opengles.airhockey.programs.ColorShaderProgram

/**
 * 空气曲棍球的木槌
 * @author: cyli8
 * @date: 2021/11/10 21:31
 */
class Mallet {
    companion object {
        private const val POSITION_COMPONENT_COUNT = 2
        private const val COLOR_COMPONENT_COUNT = 3
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT

        private val VERTEX_DATA = floatArrayOf(
            // x，y，r，g，b
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
        )
    }

    private val mVertexArray = VertexArray(Mallet.VERTEX_DATA)

    fun bindData(colorShaderProgram: ColorShaderProgram) {
        mVertexArray.setVertexAttributePointer(
            0, colorShaderProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, STRIDE
        )
        mVertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT,
            colorShaderProgram.getColorAttributeLocation(),
            COLOR_COMPONENT_COUNT, STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2)
    }

}