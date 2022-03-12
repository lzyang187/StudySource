package com.lzy.studysource.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * 通过自定义Drawable来绘制一个圆形的Drawable，并且它的半径会随着View的变化而变化，这种Drawable可以作为View的通用背景
 * Created by zhaoyang.li5 on 2022/3/12 17:12
 */
class CustomDrawable(color: Int) : Drawable() {

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = color
        }
    }

    override fun draw(canvas: Canvas) {
        val rect = bounds
        val exactCenterX = rect.exactCenterX()
        val exactCenterY = rect.exactCenterY()
        canvas.drawCircle(
            exactCenterX, exactCenterY, exactCenterX.coerceAtMost(exactCenterY), mPaint
        )
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}