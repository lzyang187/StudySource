package com.lzy.studysource.opengles.smartbook

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.lzy.studysource.opengles.airhockey.programs.BookTextureShaderProgram
import com.lzy.studysource.opengles.util.BitmapLoaderFromFile
import com.lzy.studysource.opengles.util.TextureHelper
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 电子课本的纹理渲染
 * @author: cyli8
 * @date: 2021/11/15 10:37
 */
class SmartBookTextureRender(private val mContext: Context) : GLSurfaceView.Renderer {

    private lateinit var mLeftPage: Page
    private lateinit var mRightPage: Page

    private lateinit var mTextureProgram: BookTextureShaderProgram

    private var mTexture = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        mLeftPage = Page(true)
        mRightPage = Page((false))
        mTextureProgram = BookTextureShaderProgram(mContext)
        mTexture = TextureHelper.loadTexture(
            BitmapLoaderFromFile()
        )
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        mTextureProgram.useProgram()
        mTextureProgram.setUniforms(mTexture)
        // 绘制左侧
        mLeftPage.bindData(mTextureProgram)
        mLeftPage.draw()
        // 绘制右侧
//        mRightPage.bindData(mTextureProgram)
//        mRightPage.draw()
    }
}