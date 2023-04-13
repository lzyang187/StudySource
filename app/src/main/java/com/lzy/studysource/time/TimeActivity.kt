package com.lzy.studysource.time

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R
import java.text.SimpleDateFormat
import java.util.*

class TimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        val default = TimeZone.getDefault()
        Log.e(
            TAG,
            "TimeZone: default = $default  id = ${default.id}  displayName = ${default.displayName}" + "  rawOffset = ${default.rawOffset / 1000 / 60 / 60}h"
        )
//        TimeZone.getAvailableIDs().forEach {
//            Log.e(TAG, "getAvailableIDs(): $it")
//        }

        val calendar = Calendar.getInstance()
        val data = calendar.time
        Log.e(TAG, "Calendar: ${calendar.timeZone}")
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        Log.e(
            TAG,
            "Calendar: year = $year month = $month date = $date dayOfMonth = $dayOfMonth dayOfWeek = $dayOfWeek"
        )

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Log.e(TAG, "一般日期输出: $data")
        val format = simpleDateFormat.format(data)
        Log.e(TAG, "格式化后: $format")

    }

    companion object {
        private const val TAG = "TimeActivity"
    }
}