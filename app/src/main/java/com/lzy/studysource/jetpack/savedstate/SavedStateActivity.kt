package com.lzy.studysource.jetpack.savedstate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class SavedStateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_state)

        if (savedStateRegistry.isRestored) {
            val consumeRestoredStateForKey =
                savedStateRegistry.consumeRestoredStateForKey(KEY_BUNDLE_SAVE)
            val restoreInt = consumeRestoredStateForKey?.getInt(KEY_VALUE_SAVE)
            Log.e(TAG, "restoreInt: $restoreInt")
        }
        savedStateRegistry.registerSavedStateProvider(KEY_BUNDLE_SAVE) {
            val bundle = Bundle()
            bundle.putInt(KEY_VALUE_SAVE, 1)
            Log.e(TAG, "saveInt: ")
            bundle
        }
    }

    companion object {
        private const val TAG = "SavedStateActivity"
        private const val KEY_BUNDLE_SAVE = "key_bundle_save"
        private const val KEY_VALUE_SAVE = "key_value_save"
    }
}