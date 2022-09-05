package com.lzy.studysource.fold

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class ColorMatrixDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.activity_color_matrix_detail)

        val str = intent.getStringExtra(DETAIL_EXTRA)
        val tv = findViewById<TextView>(R.id.matrix_tv)
        tv.text = str
        tv.setOnClickListener {
            AlertDialog.Builder(this).setTitle("title").setMessage("message").show()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    companion object {
        private const val TAG = "ColorMatrixDetailActivi"
        const val DETAIL_EXTRA = "detail_extra"
    }

}