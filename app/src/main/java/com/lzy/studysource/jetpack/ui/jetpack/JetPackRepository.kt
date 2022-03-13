package com.lzy.studysource.jetpack.ui.jetpack

import android.util.Log
import javax.inject.Inject

/**
 * Created by zhaoyang.li5 on 2022/3/13 18:41
 */
class JetPackRepository @Inject constructor() {

    fun load() {
        Log.i(TAG, "load: ")
    }

    companion object {
        private const val TAG = "JetPackRepository"
    }
}