package com.lzy.studysource

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class AsyncTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)

        val downloadTask = DownloadTask()
        downloadTask.execute()
        try {
            Thread.sleep(200)
        } finally {
//            downloadTask.cancel(true)
        }
    }

    private class DownloadTask : AsyncTask<URL, Int, Boolean>() {

        override fun onPreExecute() {
            Log.d(TAG, "onPreExecute: ")
        }

        override fun doInBackground(vararg params: URL?): Boolean {
            Log.d(TAG, "doInBackground: $params")
            publishProgress(50)
            Thread.sleep(500)
            return true
        }

        override fun onProgressUpdate(vararg values: Int?) {
            Log.d(TAG, "onProgressUpdate: ${values[0]}")
        }

        override fun onPostExecute(result: Boolean?) {
            Log.d(TAG, "onPostExecute: $result")
        }

        override fun onCancelled() {
            Log.d(TAG, "onCancelled: ")
        }
    }

    companion object {
        private const val TAG = "AsyncTaskActivity"
    }
}