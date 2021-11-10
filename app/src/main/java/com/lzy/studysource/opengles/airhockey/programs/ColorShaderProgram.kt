package com.lzy.studysource.opengles.airhockey.programs

import android.content.Context
import android.opengl.GLES20
import com.lzy.studysource.R

/**
 * 颜色着色器程序，用来绘制木槌
 * @author: cyli8
 * @date: 2021/11/10 21:21
 */
class ColorShaderProgram(context: Context) : ShaderProgram(
    context, R.raw.simple_vertex_shader,
    R.raw.simple_fragment_shader
) {
    // Uniform locations
    private var uMatrixLocation = 0

    // Attribute locations
    private var aPositionLocation = 0
    private var aColorLocation = 0

    init {
        // 读入和保存Uniform位置
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)
        // 读入和保存属性位置
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        aColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR)
    }

    fun setUniforms(matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

    fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }

    fun getColorAttributeLocation(): Int {
        return aColorLocation
    }

}