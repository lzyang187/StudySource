package com.lzy.studysource.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Scroller

/**
 * Scroller的用法
 * author: zyli44
 * date: 2021/7/30 11:50
 */
class ScrollerView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)


    private val mScroller = Scroller(context)


    init {
        setOnClickListener {
            mScroller.startScroll(0, 0, 500, 500, 1000)
//            smoothScrollTo(500, 500)
        }
    }

    private fun smoothScrollTo(destX: Int, destY: Int) {
        val delta = destX - scrollX
        // 1000毫秒内滑向destX，效果就是缓慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, 1000)
        invalidate()
    }

    override fun computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.currX, mScroller.currY)
//            postInvalidate()
//        }
    }

}