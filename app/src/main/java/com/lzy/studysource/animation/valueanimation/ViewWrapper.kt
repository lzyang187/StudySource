package com.lzy.studysource.animation.valueanimation

import android.view.View

/**
 *
 * 包装原始对象，间接为其提供get和set方法
 * @author: cyli8
 * @date: 2021/8/4 22:12
 */
class ViewWrapper(private val mView: View) {

    fun getWidth(): Int {
        return mView.layoutParams.width
    }

    fun setWidth(width: Int) {
        mView.layoutParams.width = width
        mView.requestLayout()
    }


}