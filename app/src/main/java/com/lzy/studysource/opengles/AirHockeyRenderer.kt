package com.lzy.studysource.opengles

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
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

        // 每个顶点的浮点数
        const val POSITION_COMPONENT_COUNT = 2

        // 每个float占用的字节
        const val BYTES_PER_FLOAT = 4
        private const val A_POSITION = "a_Position"
        private const val U_COLOR = "u_Color"
    }

    // 两个三角形组成的长方形桌子的顶点
    private val mTableVerticesWithTriangles = floatArrayOf(
        // 第一个三角形，逆时针排列
        0f, 0f,
        9f, 14f,
        0f, 14f,

        // 第二个三角形
        0f, 0f,
        9f, 0f,
        9f, 14f,

        // 中间线
        0f, 7f,
        9f, 7f,

        // 两个木槌，用两个点代替
        4.5f, 2f,
        4.5f, 12f,
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
    private var mUColorLocation = 0


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
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
        // 获取uniform的位置，稍后要更新这个uniform的时候会到mAPositionLocation
        mUColorLocation = GLES20.glGetUniformLocation(mProgramId, U_COLOR)
        // 把位置设置在数据的开头，确保OpenGL从头开始读取缓冲区的数据
        mVertexData.position(0)
        // 告诉OpenGL可以在缓冲区中找到a_Position对应的数据
        GLES20.glVertexAttribPointer(
            mAPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
            false, 0, mVertexData
        )
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

    }
}