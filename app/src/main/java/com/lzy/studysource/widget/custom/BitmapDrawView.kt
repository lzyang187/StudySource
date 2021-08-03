package com.lzy.studysource.widget.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * author: zyli44
 * date: 2021/7/30 11:50
 */
class BitmapDrawView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)


    private val mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)

    init {
        // 通过传入装载画布Bitmap对象创建Canvas对象
        val canvas = Canvas(mBitmap)
        canvas.drawColor(Color.GRAY)
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawRect(10f, 10f, 200f, 200f, paint)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(mBitmap, Matrix(), Paint())
    }

}