package com.lzy.studysource.opengles.airhockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.lzy.studysource.R
import com.lzy.studysource.opengles.airhockey.objects.Mallet
import com.lzy.studysource.opengles.airhockey.objects.Table
import com.lzy.studysource.opengles.airhockey.programs.ColorShaderProgram
import com.lzy.studysource.opengles.airhockey.programs.TextureShaderProgram
import com.lzy.studysource.opengles.util.TextureHelper
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 空气曲棍球，用纹理增加细节
 * @author: cyli8
 * @date: 2021/11/9 22:39
 */
class AirHockeyTextureRender(private val mContext: Context) : GLSurfaceView.Renderer {


    private val mProjectionMatrix = FloatArray(16)

    // 本例中的视椎体是从z为-1的位置开始的，使用模型矩阵平移把桌子移动到那个距离内，否则看不到桌子
    private val mModelMatrix = FloatArray(16)

    private lateinit var mTable: Table
    private lateinit var mMallet: Mallet

    private lateinit var mTextureProgram: TextureShaderProgram
    private lateinit var mColorShaderProgram: ColorShaderProgram

    private var mTexture = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        mTable = Table()
        mMallet = Mallet()
        mTextureProgram = TextureShaderProgram(mContext)
        mColorShaderProgram = ColorShaderProgram(mContext)
        mTexture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
//        val aspectRatio =
//            if (width > height) width.toFloat() / height.toFloat() else height.toFloat() / width.toFloat()
//        if (width > height) {
//            // 横屏，正交投影，扩展宽度的坐标空间，保持高度在-1到1的范围
//            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
//        } else {
//            // 竖屏或者是方屏，正交投影，扩展高度的坐标空间，保持宽度在-1到1的范围
//            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
//        }
        // 投影矩阵，用45度的视野创建一个透视投影，这个视椎体从z值为-1的位置开始，在z值为-10的地方结束
        Matrix.perspectiveM(mProjectionMatrix, 0, 45f, width.toFloat() / height.toFloat(), 1f, 10f)
        // 把模型矩阵设置为单位矩阵
        Matrix.setIdentityM(mModelMatrix, 0)
        // 再沿着z轴平移-3.5f
        Matrix.translateM(mModelMatrix, 0, 0f, 0f, -3.5f)
        // 旋转矩阵，绕着x轴旋转-60度
        Matrix.rotateM(mModelMatrix, 0, -60f, 1f, 0f, 0f)
        // 先创建一个临时的浮点数组用来存储临时结果
        val temp = FloatArray(16)
        // 把投影矩阵和模型矩阵相乘，结果存入temp中
        Matrix.multiplyMM(temp, 0, mProjectionMatrix, 0, mModelMatrix, 0)
        // 把结果存回mProjectionMatrix，它现在包含模型矩阵与投影矩阵的组合效应
        System.arraycopy(temp, 0, mProjectionMatrix, 0, temp.size)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        // 绘制桌子部分
        mTextureProgram.useProgram()
        mTextureProgram.setUniforms(mProjectionMatrix, mTexture)
        mTable.bindData(mTextureProgram)
        mTable.draw()
        // 绘制木槌
        mColorShaderProgram.useProgram()
        mColorShaderProgram.setUniforms(mProjectionMatrix)
        mMallet.bindData(mColorShaderProgram)
        mMallet.draw()
    }
}