package com.lzy.studysource.opengles.airhockey.programs

import android.content.Context
import android.opengl.GLES20
import com.lzy.studysource.R

/**
 * 颜色着色器程序，构建简单物体章节，为每个顶点的位置而不是每个顶点的颜色定义了冰球和木槌，
 * 所有我们不得不把颜色作为一个uniform传递进去
 * @author: cyli8
 * @date: 2021/11/10 21:21
 */
class ColorShaderProgramWithoutColor(context: Context) : ShaderProgram(
    context, R.raw.simple_vertex_shader_without_color,
    R.raw.simple_fragment_shader_without_color
) {

    companion object {
        const val U_COLOR = "u_Color"
    }

    // Uniform locations
    private var uMatrixLocation = 0
    private var uColorLocation = 0

    // Attribute locations
    private var aPositionLocation = 0

    init {
        // 读入和保存Uniform位置
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)
        uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR)
        // 读入和保存属性位置
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
    }

    fun setUniforms(matrix: FloatArray, r: Float, g: Float, b: Float) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        GLES20.glUniform4f(uColorLocation, r, g, b, 1f)
    }

    fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }

}