package com.lzy.studysource.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup

/**
 * 全局随手指滑动。使用动画和改变布局参数实现。
 * 因为scrollTo和scrollBy只能移动view的内容而不能移动位置，所以无法直接实现
 * author: zyli44
 * date: 2021/8/15 16:40
 */
class FollowTouchView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    private var mLastX = 0f
    private var mLastY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastX = it.x
                    mLastY = it.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = it.x - mLastX
                    val dY = it.y - mLastY
                    // 第一种layout方法：在当前layout的left、top、right、bottom基础上，增加计算出来的偏移量
//                    layout(
//                        (left + dX).toInt(), (top + dY).toInt(), (right + dX).toInt(),
//                        (bottom + dY).toInt()
//                    )
                    // 第二种方法：offsetLeftAndRight同时对left和right进行偏移
//                    offsetLeftAndRight(dX.toInt())
//                    offsetTopAndBottom(dY.toInt())
                    // 第三种方法：1、LayoutParams，前提是必须要有父布局，不然或取不到LayoutParams
//                    val layoutParams = layoutParams as ConstraintLayout.LayoutParams
//                    layoutParams.leftMargin = (left + dX).toInt()
//                    layoutParams.topMargin = (top + dY).toInt()
//                    setLayoutParams(layoutParams)
                    // 2、不用考虑具体的父布局类型的方式
                    val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
                    marginLayoutParams.leftMargin = (left + dX).toInt()
                    marginLayoutParams.topMargin = (top + dY).toInt()
                    layoutParams = marginLayoutParams
                    // 第四种方法

                }
            }
        }
        return true
    }

    companion object {
        private const val TAG = "FollowTouchView"
    }

}