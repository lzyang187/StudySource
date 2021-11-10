package com.lzy.studysource.opengles.airhockey.programs

import android.content.Context
import android.opengl.GLES20
import com.lzy.studysource.R

/**
 * 加入纹理着色器程序，用来绘制桌子
 * @author: cyli8
 * @date: 2021/11/10 08:33
 */
class TextureShaderProgram(context: Context) : ShaderProgram(
    context, R.raw.texture_vertex_shader,
    R.raw.texture_fragment_shader
) {

    // Uniform locations
    private var uMatrixLocation = 0
    private var uTextureUnitLocation = 0

    // Attribute locations
    private var aPositionLocation = 0
    private var aTextureCoordinatesLocation = 0

    init {
        // 读入和保存Uniform位置
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)
        uTextureUnitLocation = GLES20.glGetUniformLocation(mProgram, U_TEXTURE_UNIT)
        // 读入和保存属性位置
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES)
    }

    /**
     * 设置uniform并返回属性位置
     */
    fun setUniforms(matrix: FloatArray, textureId: Int) {
        // 传递矩阵给它的uniform
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        // 把活动的纹理单元设置为纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        // 把纹理单元绑定到这个单元
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        // 把选定的纹理单元传递给片段着色器中的u_TextureUnit
        GLES20.glUniform1i(uTextureUnitLocation, 0)
    }

    fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }

    fun getTextureCoordinatesAttributeLocation(): Int {
        return aTextureCoordinatesLocation
    }


}