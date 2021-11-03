package com.lzy.studysource.opengles.util

import android.opengl.GLES20
import android.util.Log

/**
 * 着色器帮助类
 * @author: cyli8
 * @date: 2021/11/3 23:07
 */
object ShaderHelper {
    private const val TAG = "ShaderHelper"

    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {
        // 创建一个新的着色器对象，并把这个对象的ID存入shaderObjectId
        val shaderObjectId = GLES20.glCreateShader(type)
        // 返回0表示创建失败
        if (shaderObjectId == 0) {
            Log.e(TAG, "compileShader: 无法创建着色器")
            return 0
        }
        // 告诉OpenGL读入shaderCode的源码，并把它与shaderObjectId所引用的着色器关联起来
        GLES20.glShaderSource(shaderObjectId, shaderCode)
        // 编译这个着色器
        GLES20.glCompileShader(shaderObjectId)
        // 检查是否成功编译了这个着色器
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            Log.e(TAG, "compileShader: 编译着色器失败")
            // 告诉OpenGL删除这个着色器对象
            GLES20.glDeleteShader(shaderObjectId)
            // 取出着色器信息日志
            val glGetShaderInfoLog = GLES20.glGetShaderInfoLog(shaderObjectId)
            Log.i(TAG, "compileShader: glGetShaderInfoLog = $glGetShaderInfoLog")
            return 0
        }
        return shaderObjectId
    }

    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        // 新建程序对象
        val programObjectId = GLES20.glCreateProgram()
        if (programObjectId == 0) {
            Log.e(TAG, "linkProgram: 无法创建程序对象")
            return 0
        }
        // 把顶点着色器和片段着色器附加到程序对象上
        GLES20.glAttachShader(programObjectId, vertexShaderId)
        GLES20.glAttachShader(programObjectId, fragmentShaderId)
        // 链接程序
        GLES20.glLinkProgram(programObjectId)
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            Log.e(TAG, "linkProgram: 链接程序失败")
            // 删除这个程序对象
            GLES20.glDeleteProgram(programObjectId)
            // 取出程序对象的信息
            val glGetProgramInfoLog = GLES20.glGetProgramInfoLog(programObjectId)
            Log.i(TAG, "linkProgram: glGetProgramInfoLog = $glGetProgramInfoLog")
        }
        return programObjectId
    }

    /**
     * 应该只有在开发或调试应用的时候才调用
     */
    fun validateProgram(programId: Int): Boolean {
        GLES20.glValidateProgram(programId)
        val validateStatus = IntArray(1)
        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)
        val glGetProgramInfoLog = GLES20.glGetProgramInfoLog(programId)
        Log.i(TAG, "validateProgram: glGetProgramInfoLog = $glGetProgramInfoLog")
        return validateStatus[0] != 0
    }

}