package com.lzy.studysource.fold

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.window.embedding.SplitController
import com.lzy.studysource.R
import com.lzy.studysource.colormatrix.ColorMatrixListActivity

/**
 * 平行视界的demo
 */
class EmbeddingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_embedding)

        // 是否支持分屏
        val splitSupported = SplitController.Companion.getInstance().isSplitSupported()
        Log.d(TAG, "splitSupported: $splitSupported")

//        startActivity(Intent(this, ColorMatrixListActivity::class.java))

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
    }

    companion object {
        private const val TAG = "EmbeddingActivity"

    }
}