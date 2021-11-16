package com.lzy.studysource.opengles.smartbook

import android.opengl.GLES20
import com.lzy.studysource.opengles.airhockey.data.Constants
import com.lzy.studysource.opengles.airhockey.data.VertexArray
import com.lzy.studysource.opengles.airhockey.programs.BookTextureShaderProgram

/**
 * 电子课本的页
 * @author: cyli8
 * @date: 2021/11/15 10:37
 */
class Page(private val left: Boolean) {

    companion object {
        // 每个顶点中位置的浮点数
        private const val POSITION_COMPONENT_COUNT = 2

        // 每个顶点中纹理坐标的浮点数
        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2

        // 跨距
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT

        // 顶点数据，X，Y，S，T，三角形扇的形式
        // T分量和Y分量的方向定义是相反的
        private val LEFT_VERTEX_DATA = floatArrayOf(
            -0.5f, 0f, 0.5f, 0.5f,
            -1f, -1f, 0f, 1f,
            0f, -1f, 1f, 1f,
            0f, 1f, 1f, 0f,
            -1f, 1f, 0f, 0f,
            -1f, -1f, 0f, 1f,
        )

        private val RIGHT_VERTEX_DATA = floatArrayOf(
            0.5f, 0f, 0.5f, 0.5f,
            0f, -1f, 0f, 1f,
            1f, -1f, 1f, 1f,
            1f, 1f, 1f, 0f,
            0f, 1f, 0f, 0f,
            0f, -1f, 0f, 1f,
        )
    }

    private val mVertexArray = if (left) VertexArray(LEFT_VERTEX_DATA) else VertexArray(
        RIGHT_VERTEX_DATA
    )

    /**
     * 把顶点数组绑定到一个着色器程序上
     */
    fun bindData(textureProgram: BookTextureShaderProgram) {
        mVertexArray.setVertexAttributePointer(
            0, textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE
        )
        mVertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT,
            textureProgram.getTextureCoordinatesAttributeLocation(),
            TEXTURE_COORDINATES_COMPONENT_COUNT,
            STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)
    }
}