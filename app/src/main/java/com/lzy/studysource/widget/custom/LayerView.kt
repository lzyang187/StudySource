package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * canvas图层的使用
 * author: zyli44
 * date: 2021/8/21 14:51
 */
class LayerView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.FILL
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            mPaint.color = Color.BLUE
            it.drawCircle(150f, 150f, 100f, mPaint)
            // 图层入栈，后面的操作发生在这个新入栈的图层上
            it.saveLayer(0f, 0f, 400f, 400f, mPaint)
//            it.saveLayerAlpha(0f, 0f, 400f, 400f, 127)
            mPaint.color = Color.RED
            it.drawCircle(200f, 200f, 100f, mPaint)
            // 出栈，会把图像绘制到上层canvas上
            it.restore()

        }
    }

}