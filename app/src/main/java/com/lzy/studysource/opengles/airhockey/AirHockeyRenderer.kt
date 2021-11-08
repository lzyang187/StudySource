package com.lzy.studysource.opengles.airhockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.lzy.studysource.R
import com.lzy.studysource.opengles.util.ShaderHelper
import com.lzy.studysource.opengles.util.TextResourceRender
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 空气曲棍球
 * @author: cyli8
 * @date: 2021/11/3 08:27
 */
class AirHockeyRenderer(private val mContext: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "AirHockeyRenderer"

        // 每个顶点中位置的浮点数
        private const val POSITION_COMPONENT_COUNT = 2

        // 每个顶点中颜色的浮点数
        private const val COLOR_COMPONENT_COUNT = 3

        // 每个float占用的字节
        private const val BYTES_PER_FLOAT = 4

        private const val A_POSITION = "a_Position"
        private const val A_COLOR = "a_Color"
        private const val U_MATRIX = "u_Matrix"

        // 跨距，告诉OpenGL每个位置之间有多少个字节
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT
    }

    // 两个三角形组成的长方形桌子的顶点
    private val mTableVerticesWithTriangles = floatArrayOf(
        // 第一个三角形，逆时针排列
//        -0.5f, -0.5f,
//        0.5f, 0.5f,
//        -0.5f, 0.5f,

        // 第二个三角形
//        -0.5f, -0.5f,
//        0.5f, -0.5f,
//        0.5f, 0.5f,

        // 三角形扇的方式
        // x, y, r, g, b
//        0f, 0f, 1f, 1f, 1f,
//        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
//        0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
//        0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
//        -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
//        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

        // 适配横屏后，需要使桌子高度变高，只更新y的位置（第二列）
        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

        // 中间线
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 0f, 0f, 1f,

        // 两个木槌，用两个点代替
        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    // OpenGL作为本地系统库直接运行在硬件上，没有虚拟机、垃圾回收器，所以需要把内存从Java堆复制到本地堆
    private val mVertexData =
        ByteBuffer.allocateDirect(mTableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

    init {
        // put方法把数据从Dalvik内存复制到本地内存
        mVertexData.put(mTableVerticesWithTriangles)
    }

    private var mProgramId = 0
    private var mAPositionLocation = 0
    private var mAColorLocation = 0
    private val mProjectionMatrix = FloatArray(16)
    private var mUMatrixLocation = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        val vertexShaderSource =
            TextResourceRender.readTextFileFromResource(mContext, R.raw.simple_vertex_shader)
        Log.d(TAG, "onSurfaceCreated: vertexShaderSource = $vertexShaderSource")
        val fragmentShaderSource =
            TextResourceRender.readTextFileFromResource(mContext, R.raw.simple_fragment_shader)
        Log.d(TAG, "onSurfaceCreated: fragmentShaderSource = $fragmentShaderSource")
        val vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        mProgramId = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId)
        Log.d(TAG, "onSurfaceCreated: mProgramId = $mProgramId")
        ShaderHelper.validateProgram(mProgramId)
        // 告诉OpenGL在绘制任何东西到屏幕上的时候要使用这里定义的程序
        GLES20.glUseProgram(mProgramId)
        // 获取属性位置，就能告诉OpenGL到哪里去找到这个属性对应的数据了
        mAPositionLocation = GLES20.glGetAttribLocation(mProgramId, A_POSITION)
        // 把位置设置在数据的开头，确保OpenGL从头开始读取缓冲区的数据
        mVertexData.position(0)
        // 告诉OpenGL可以在缓冲区中找到a_Position对应的数据
        GLES20.glVertexAttribPointer(
            mAPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
            false, STRIDE, mVertexData
        )
        // 使能顶点数组
        GLES20.glEnableVertexAttribArray(mAPositionLocation)

        // 获取uniform的位置，稍后要更新这个uniform的时候会到mAPositionLocation
//        mUColorLocation = GLES20.glGetUniformLocation(mProgramId, U_COLOR)
        mAColorLocation = GLES20.glGetAttribLocation(mProgramId, A_COLOR)
        // 当OpenGL读入颜色时需要跳过位置属性
        mVertexData.position(POSITION_COMPONENT_COUNT)
        // 把颜色数据和片段着色器中的a_Color关联起来
        GLES20.glVertexAttribPointer(
            mAColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT,
            false, STRIDE, mVertexData
        )
        // 为颜色属性使能顶点属性数组
        GLES20.glEnableVertexAttribArray(mAColorLocation)

        // 把正交投影矩阵和顶点着色器的u_Matrix关联起来
        mUMatrixLocation = GLES20.glGetUniformLocation(mProgramId, U_MATRIX)
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val aspectRatio =
            if (width > height) width.toFloat() / height.toFloat() else height.toFloat() / width.toFloat()
        if (width > height) {
            // 横屏，正交投影，扩展宽度的坐标空间，保持高度在-1到1的范围
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
        } else {
            // 竖屏或者是方屏，正交投影，扩展高度的坐标空间，保持宽度在-1到1的范围
            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        // 绘制桌子部分
        // 更新着色器代码中的u_Color值，这里是白色
//        GLES20.glUniform4f(mUColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        // 告诉OpenGL我们要画三角形，使用顶点数组索引为0到5共六个顶点，这里会画两个三角形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)
        // 绘制中间线部分，这里是红色
//        GLES20.glUniform4f(mUColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        // 画线
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)
        // 绘制连个木槌点
        // 绘制第一个木槌点，蓝色
//        GLES20.glUniform4f(mUColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        // 画点
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)
        // 绘制第二个木槌点，红色
//        GLES20.glUniform4f(mUColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)
        // 传递矩阵给顶点着色器
        GLES20.glUniformMatrix4fv(mUMatrixLocation, 1, false, mProjectionMatrix, 0)
    }
}