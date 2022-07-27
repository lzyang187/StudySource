package com.lzy.studysource.coroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * Created by zhaoyang.li5 on 2022/7/21 11:31
 */
class CoroutineViewModel : ViewModel() {

    val mTestFlow: MutableStateFlow<String> = MutableStateFlow("")

    fun testFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 超时逻辑
                withTimeout(3000) {
                    Log.e(TAG, "testFlow: " + Thread.currentThread().name)
                    for (i in 0..3) {
                        delay(1000)
                        Log.e(TAG, "emit: $i")
                        mTestFlow.emit("$i")
                    }
                }
            } catch (e: Exception) {
                mTestFlow.emit(TimeoutException("超时啦").toString())
                // 超时异常
                Log.e(TAG, "testFlow: $e")
            }
        }
    }

    fun testFlowOf(): Flow<String> {
        return flow {
            Log.d(TAG, "testFlowOf: emit a")
            emit("a")
            delay(1000)
            Log.d(TAG, "testFlowOf: emit b")
            emit("b")
        }
    }

    companion object {
        private const val TAG = "CoroutineTagViewModel"
    }

}