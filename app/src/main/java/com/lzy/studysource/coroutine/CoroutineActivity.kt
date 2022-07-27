package com.lzy.studysource.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lzy.studysource.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {

    private val mViewModel: CoroutineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        val tv = findViewById<TextView>(R.id.tv)
        lifecycleScope.launch {
            // repeatOnLifecycle 是一个接收 Lifecycle.State 作为参数的挂起函数，
            // 该 API 具有生命周期感知能力，所以能够在当生命周期进入响应状态时自动使用传递给它的代码块启动新的协程，
            // 并且在生命周期离开该状态时取消该协程。
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 直到生命周期进入 DESTROYED，调用 repeatOnLifecycle 的协程都不会恢复执行，
                // 因此如果您需要从多个数据流中进行收集，则应在 repeatOnLifecycle 代码块内多次使用 launch 来创建协程
                launch {
                    mViewModel.mTestFlow.onStart {
                        Log.d(TAG, "onStart: ")
                    }.onCompletion {
                        Log.d(TAG, "onCompletion: ")
                    }.catch {
                        Log.d(TAG, "catch: ")
                    }.collect {
                        Log.d(TAG, "collect: $it")
                        tv.text = it
                    }
                }
            }
        }

        lifecycleScope.launch {
            delay(1000)
            Log.d(TAG, "launch：" + Thread.currentThread().name)
//            mViewModel.testFlow()
        }

        lifecycleScope.launch {
            mViewModel.testFlowOf().map {
                Log.d(TAG, "testFlowOf map: $it")
                it.uppercase()
            }.filter {
                Log.d(TAG, "testFlowOf filter: $it")
                it == "B"
            }.flowOn(Dispatchers.IO).onStart {
                Log.d(TAG, "testFlowOf onStart")
            }.onCompletion {
                Log.d(TAG, "testFlowOf onCompletion")
            }.onEach {
                Log.d(TAG, "testFlowOf onEach: $it")
            }.collect {
                Log.d(TAG, "testFlowOf collect: $it")
            }
        }
    }

    companion object {
        private const val TAG = "CoroutineTagActivity"
    }
}