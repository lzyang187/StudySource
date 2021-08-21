package com.lzy.studysource.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller

/**
 * Scroller的用法，在手指抬起时回到原点
 * author: zyli44
 * date: 2021/7/30 11:50
 */
class ScrollerView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)


    // 第一步：初始化
    private val mScroller = Scroller(context)

    // 第二步：重写computeScroll
    override fun computeScroll() {
        super.computeScroll()
        // 判断是否完成了整个滑动
        if (mScroller.computeScrollOffset()) {
            val parentView = parent as View
            // 实际使用scrollTo方法，getCurrX和getCurrY获取的是当前的滑动坐标
            parentView.scrollTo(mScroller.currX, mScroller.currY)
            // 需要不断重绘触发调用computeScroll
            postInvalidate()
        }
    }

    private var mLastX = 0f
    private var mLastY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastX = it.x
                    mLastY = it.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = it.x - mLastX
                    val dy = it.y - mLastY
                    val view = parent as View
                    // 通过执行父View的scrollBy实现滑动
                    view.scrollBy((-dx).toInt(), (-dy).toInt())
                }
                MotionEvent.ACTION_UP -> {
                    val parentView = parent as View
                    // 第三步：手指离开时执行滑动过程
                    mScroller.startScroll(
                        parentView.scrollX,
                        parentView.scrollY,
                        -parentView.scrollX,
                        -parentView.scrollY,
                        1000
                    )
                    // 不要忘了重绘
                    invalidate()
                }
            }
        }
        return true
    }

}