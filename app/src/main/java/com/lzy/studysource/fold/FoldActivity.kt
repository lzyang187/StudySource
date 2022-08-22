package com.lzy.studysource.fold

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import androidx.window.layout.WindowMetricsCalculator
import com.lzy.studysource.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        setContentView(R.layout.activity_fold)
        val slidingPaneLayout = findViewById<SlidingPaneLayout>(R.id.sliding_pane_layout)
        val switch = findViewById<SwitchCompat>(R.id.switch_view)
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val openPane = slidingPaneLayout.openPane()
                Log.e(TAG, "onCreate: openPane = $openPane")
            } else {
                val closePane = slidingPaneLayout.closePane()
                Log.e(TAG, "onCreate: closePane = $closePane")
            }
        }

        var foldFragment1 = supportFragmentManager.findFragmentByTag("fold_fragment_1")
        if (foldFragment1 == null) {
            foldFragment1 = FoldFragment1()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_1, foldFragment1, "fold_fragment_1")
            .commitNowAllowingStateLoss()

        slidingPaneLayout.addPanelSlideListener(object : SlidingPaneLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i(TAG, "onPanelSlide:panel = $panel slideOffset = $slideOffset")
            }

            override fun onPanelOpened(panel: View) {
                Log.i(TAG, "onPanelOpened: ")
            }

            override fun onPanelClosed(panel: View) {
                Log.i(TAG, "onPanelClosed: ")
            }
        })
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_UNLOCKED


        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(this@FoldActivity).windowLayoutInfo(this@FoldActivity)
                    .collect { newLayoutInfo: WindowLayoutInfo ->
                        // Use newLayoutInfo to update the layout.
                        Log.w(TAG, "newLayoutInfo:")
                        val foldingFeature =
                            newLayoutInfo.displayFeatures.filterIsInstance<FoldingFeature>()
                                .firstOrNull()
                        if (foldingFeature != null) {
                            Log.w(
                                TAG,
                                "FoldingFeature: state = ${foldingFeature.state} orientation = ${foldingFeature.orientation} occlusionType = ${foldingFeature.occlusionType} isSeparating = ${foldingFeature.isSeparating}"
                            )
                        }
                        when {
                            isTableTopPosture(foldingFeature) -> enterTabletopMode(foldingFeature)
                            isBookPosture(foldingFeature) -> enterBookMode(foldingFeature)
                            isSeparating(foldingFeature) ->
                                // Dual-screen device
                                if (foldingFeature?.orientation == FoldingFeature.Orientation.HORIZONTAL) {
                                    enterTabletopMode(foldingFeature)
                                } else {
                                    enterBookMode(foldingFeature)
                                }
                            else -> enterNormalMode()
                        }
                    }
            }
        }
    }

    private fun enterNormalMode() {
        Log.w(TAG, "enterNormalMode: ")
    }

    private fun enterBookMode(foldingFeature: FoldingFeature?) {
        Log.w(TAG, "enterBookMode: ")
    }

    private fun enterTabletopMode(foldingFeature: FoldingFeature?) {
        Log.w(TAG, "enterTabletopMode: ")
    }

    private fun isTableTopPosture(foldFeature: FoldingFeature?): Boolean {
        return foldFeature?.state == FoldingFeature.State.HALF_OPENED && foldFeature.orientation == FoldingFeature.Orientation.HORIZONTAL
    }

    private fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
        return foldFeature?.state == FoldingFeature.State.HALF_OPENED && foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
    }

    private fun isSeparating(foldFeature: FoldingFeature?): Boolean {
        return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val bounds = windowMetrics.bounds
        Log.w(TAG, "onConfigurationChanged: bounds = $bounds")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    companion object {
        private const val TAG = "FoldActivity"
    }
}