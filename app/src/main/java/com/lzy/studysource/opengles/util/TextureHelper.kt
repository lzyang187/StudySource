package com.lzy.studysource.opengles.util

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log

/**
 * 纹理的帮助类
 * @author: cyli8
 * @date: 2021/11/9 22:42
 */
object TextureHelper {
    private const val TAG = "TextureHelper"

    /**
     * 从资源中读入文件，并把图形数据加载进OpenGL
     */
    fun loadTexture(context: Context, resourceId: Int): Int {
        val textureObjectIds = IntArray(1)
        // 创建一个纹理对象，
        GLES20.glGenTextures(1, textureObjectIds, 0)
        if (textureObjectIds[0] == 0) {
            Log.e(TAG, "loadTexture: 无法创建纹理对象")
            return 0
        }
        // 加载位图
        val options = BitmapFactory.Options()
        // 要原始数据，而不是缩放版本
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)
        if (bitmap == null) {
            Log.e(TAG, "loadTexture: 图片资源无法加载")
            // 删除纹理对象
            GLES20.glDeleteTextures(1, textureObjectIds, 0)
            return 0
        }
        // 绑定纹理：告诉OpenGL后面的纹理调用应该用这个纹理对象
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0])
        // 设置默认的纹理过滤参数
        // 缩小的情况，使用三线性过滤GL_LINEAR_MIPMAP_LINEAR
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR_MIPMAP_LINEAR
        )
        // 放大的情况，使用双线性过滤GL_LINEAR
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        // 告诉OpenGL读入bitmap定义的位图数据，并把它复制到当前绑定的纹理对象
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        // 已经加载进OpenGL，位图就可以回收了
        bitmap.recycle()
        // 生成所有必要的级别
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
        // 完成纹理加载后，传入0与当前的纹理解除绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        return textureObjectIds[0]
    }
}