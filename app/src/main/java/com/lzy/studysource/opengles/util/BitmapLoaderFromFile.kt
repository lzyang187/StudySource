package com.lzy.studysource.opengles.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

/**
 * @author: zyli44
 * @date: 2021/11/15 11:03
 * @description:
 */
class BitmapLoaderFromFile() : IBitmapLoader {

    override fun loadBitmap(): Bitmap? {
        // 加载位图
        val options = BitmapFactory.Options()
        // 要原始数据，而不是缩放版本
        options.inScaled = false
        val file = File(
            Environment.getExternalStorageDirectory(), "smartBook/0501010101-2320_21/image/2.jpg"
        )
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

}