package com.lzy.studysource.opengles.airhockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.lzy.studysource.R
import com.lzy.studysource.opengles.airhockey.objects.MalletBatter
import com.lzy.studysource.opengles.airhockey.objects.Puck
import com.lzy.studysource.opengles.airhockey.objects.Table
import com.lzy.studysource.opengles.airhockey.programs.ColorShaderProgramWithoutColor
import com.lzy.studysource.opengles.airhockey.programs.TextureShaderProgram
import com.lzy.studysource.opengles.util.BitmapLoaderFromResource
import com.lzy.studysource.opengles.util.TextureHelper
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 空气曲棍球，构建简单物体
 * @author: cyli8
 * @date: 2021/11/9 22:39
 */
class AirHockeyBatterMalletsRender(private val mContext: Context) : GLSurfaceView.Renderer {


    private val mProjectionMatrix = FloatArray(16)

    // 存储视图矩阵
    private val mViewMatrix = FloatArray(16)
    private val mViewProjectionMatrix = FloatArray(16)
    private val mModelViewProjectionMatrix = FloatArray(16)

    // 本例中的视椎体是从z为-1的位置开始的，使用模型矩阵平移把桌子移动到那个距离内，否则看不到桌子
    private val mModelMatrix = FloatArray(16)

    private lateinit var mTable: Table
    private lateinit var mMallet: MalletBatter
    private lateinit var mPuck: Puck

    private lateinit var mTextureProgram: TextureShaderProgram
    private lateinit var mColorShaderProgram: ColorShaderProgramWithoutColor

    private var mTexture = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        mTable = Table()
        mMallet = MalletBatter(0.08f, 0.15f, 32)
        mPuck = Puck(0.06f, 0.02f, 32)
        mTextureProgram = TextureShaderProgram(mContext)
        mColorShaderProgram = ColorShaderProgramWithoutColor(mContext)
        mTexture = TextureHelper.loadTexture(
            BitmapLoaderFromResource(
                mContext,
                R.drawable.air_hockey_surface
            )
        )
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
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        Matrix.multiplyMM(mViewProjectionMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        // 绘制桌子部分
        positionTableScene()
        mTextureProgram.useProgram()
        mTextureProgram.setUniforms(mModelViewProjectionMatrix, mTexture)
        mTable.bindData(mTextureProgram)
        mTable.draw()

        // 绘制木槌1
        positionObjectInScene(0f, mMallet.height / 2f, -0.4f)
        mColorShaderProgram.useProgram()
        mColorShaderProgram.setUniforms(mModelViewProjectionMatrix, 1f, 0f, 0f)
        mMallet.bindData(mColorShaderProgram)
        mMallet.draw()

        // 绘制木槌2
        positionObjectInScene(0f, mMallet.height / 2f, 0.4f)
        mColorShaderProgram.setUniforms(mModelViewProjectionMatrix, 0f, 0f, 1f)
        mMallet.draw()

        // 绘制冰球
        positionObjectInScene(0f, mPuck.height / 2f, 0f)
        mColorShaderProgram.setUniforms(mModelViewProjectionMatrix, 0.8f, 0.8f, 1f)
        mPuck.bindData(mColorShaderProgram)
        mPuck.draw()
    }

    private fun positionTableScene() {
        Matrix.setIdentityM(mModelMatrix, 0)
        Matrix.rotateM(mModelMatrix, 0, -90f, 1f, 0f, 0f)
        Matrix.multiplyMM(mModelViewProjectionMatrix, 0, mViewProjectionMatrix, 0, mModelMatrix, 0)
    }

    private fun positionObjectInScene(x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(mModelMatrix, 0)
        Matrix.translateM(mModelMatrix, 0, x, y, z)
        Matrix.multiplyMM(mModelViewProjectionMatrix, 0, mViewProjectionMatrix, 0, mModelMatrix, 0)
    }

}