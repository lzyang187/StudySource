package com.lzy.studysource.opengles.airhockey.objects

import android.opengl.GLES20
import com.lzy.studysource.opengles.airhockey.data.Constants
import com.lzy.studysource.opengles.airhockey.data.VertexArray
import com.lzy.studysource.opengles.airhockey.programs.TextureShaderProgram

/**
 * 空气曲棍球的桌子
 * @author: cyli8
 * @date: 2021/11/10 07:44
 */
class Table {

    companion object {
        // 每个顶点中位置的浮点数
        private const val POSITION_COMPONENT_COUNT = 2

        // 每个顶点中纹理坐标的浮点数
        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2

        // 跨距
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT

        // 顶点数据，X，Y，S，T，三角形扇的形式
        // T分量和Y分量的方向定义是相反的，另外使用范围0.1到0.9剪裁了它的边缘
        private val VERTEX_DATA = floatArrayOf(
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f,
        )
    }

    private val mVertexArray = VertexArray(VERTEX_DATA)

    /**
     * 把顶点数组绑定到一个着色器程序上
     */
    fun bindData(textureProgram: TextureShaderProgram) {
        mVertexArray.setVertexAttributePointer(
            0, textureProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, STRIDE
        )
        mVertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT, textureProgram.getTextureCoordinatesAttributeLocation(),
            TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)
    }
}