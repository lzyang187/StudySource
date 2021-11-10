package com.lzy.studysource.opengles.airhockey.data

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * 封装存储顶点矩阵的FloatBuffer
 * @author: cyli8
 * @date: 2021/11/9 23:27
 */
class VertexArray(private val vertexData: FloatArray) {

    private val mFloatBuffer by lazy {
        ByteBuffer.allocateDirect(vertexData.size * Constants.BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData)
    }

    fun setVertexAttributePointer(
        dataOffset: Int, attributeLocation: Int, componentCount: Int,
        stride: Int
    ) {
        mFloatBuffer.position(dataOffset)
        GLES20.glVertexAttribPointer(
            attributeLocation, componentCount, GLES20.GL_FLOAT,
            false, stride, mFloatBuffer
        )
        GLES20.glEnableVertexAttribArray(attributeLocation)
        mFloatBuffer.position(0)
    }

}