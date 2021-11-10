package com.lzy.studysource.opengles.airhockey.programs

import android.content.Context
import android.opengl.GLES20
import com.lzy.studysource.opengles.util.ShaderHelper
import com.lzy.studysource.opengles.util.TextResourceRender

/**
 * 使用指定的着色器构建一个OpenGL着色器程序
 * @author: cyli8
 * @date: 2021/11/10 08:38
 */
open class ShaderProgram(
    context: Context,
    vertexShaderResourceId: Int,
    fragmentShaderResourceId: Int
) {

    companion object {
        // Uniform 常量
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"

        // Attribute 常量
        const val A_POSITION = "a_Position"
        const val A_COLOR = "a_Color"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    }

    // 着色器程序
    protected var mProgram: Int = 0

    init {
        mProgram = ShaderHelper.buildProgram(
            TextResourceRender.readTextFileFromResource(context, vertexShaderResourceId),
            TextResourceRender.readTextFileFromResource(context, fragmentShaderResourceId)
        )
    }

    fun useProgram() {
        GLES20.glUseProgram(mProgram)
    }

}