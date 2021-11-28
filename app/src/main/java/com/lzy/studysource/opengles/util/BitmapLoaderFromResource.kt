package com.lzy.studysource.opengles.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * @author: zyli44
 * @date: 2021/11/15 11:03
 * @description:
 */
class BitmapLoaderFromResource(private val context: Context, private val resourceId: Int) :
    IBitmapLoader {

    override fun loadBitmap(): Bitmap? {
        // 加载位图
        val options = BitmapFactory.Options()
        // 要原始数据，而不是缩放版本
        options.inScaled = false
        return BitmapFactory.decodeResource(context.resources, resourceId, options)
    }

}