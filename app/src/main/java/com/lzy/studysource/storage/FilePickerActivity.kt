package com.lzy.studysource.storage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

/**
 * 打开系统的文件选择器应用
 */
class FilePickerActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "FilePickerActivity"

        private const val PICK_IMAGE_FILE = 1
        private const val PICK_AUDIO_FILE = 2
        private const val PICK_VIDEO_FILE = 3
        private const val PICK_DOC_FILE = 4
        private const val PICK_ALL_FILE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_picker)
        findViewById<View>(R.id.image_picker).setOnClickListener {
            openFile("image/*", PICK_IMAGE_FILE)
        }
        findViewById<View>(R.id.video_picker).setOnClickListener {
            // Launch the photo picker and let the user choose only videos.
            openFile("video/*", PICK_VIDEO_FILE)
        }
        findViewById<View>(R.id.audio_picker).setOnClickListener {
            openFile("audio/*", PICK_AUDIO_FILE)
        }
        findViewById<View>(R.id.doc_picker).setOnClickListener {
            val mimeTypes = arrayOf(
                "application/pdf",
                "text/*",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                // 金山wps
                "application/kswps",
                "application/kset",
                "application/ksdps"
            )
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
            startActivityForResult(intent, PICK_DOC_FILE)
        }
        findViewById<View>(R.id.all_picker).setOnClickListener {
            openFile("*/*", PICK_ALL_FILE)
        }
    }

    fun openFile(mimeType: String, requestCode: Int, pickerInitialUri: Uri? = null) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //  指定文件选择器在首次加载时应显示的文件的 URI
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }

        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_IMAGE_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                // The result data contains a URI for the document or directory that
                // the user selected.
                resultData?.data?.also { uri ->
                    // Perform operations on the document using its URI.
                    Log.e(TAG, "onActivityResult: PICK_IMAGE_FILE $uri")
                }
            }
        } else if (requestCode == PICK_AUDIO_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                resultData?.data?.also { uri ->
                    // Perform operations on the document using its URI.
                    Log.e(TAG, "onActivityResult: PICK_AUDIO_FILE $uri")
                }
            }
        } else if (requestCode == PICK_VIDEO_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                resultData?.data?.also { uri ->
                    // Perform operations on the document using its URI.
                    Log.e(TAG, "onActivityResult: PICK_VIDEO_FILE $uri")
                }
            }
        } else if (requestCode == PICK_DOC_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                resultData?.data?.also { uri ->
                    // Perform operations on the document using its URI.
                    Log.e(TAG, "onActivityResult: PICK_DOC_FILE $uri")
                }
            }
        } else if (requestCode == PICK_ALL_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                resultData?.data?.also { uri ->
                    // Perform operations on the document using its URI.
                    Log.e(TAG, "onActivityResult: PICK_ALL_FILE $uri")
                }
            }
        }
    }
}