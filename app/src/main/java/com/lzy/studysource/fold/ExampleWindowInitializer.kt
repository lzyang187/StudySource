package com.lzy.studysource.fold

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.window.embedding.SplitController
import com.lzy.studysource.R

/**
 * Created by zhaoyang.li5 on 2022/9/4 10:40
 */
class ExampleWindowInitializer : Initializer<SplitController> {

    override fun create(context: Context): SplitController {
        SplitController.initialize(context, R.xml.main_split_config)
        Log.d(TAG, "create: ")
        return SplitController.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

    companion object {
        private const val TAG = "ExampleWindowInitialize"
    }
}