package com.lzy.studysource.downloadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;

import java.io.File;

public class DownloadManagerActivity extends AppCompatActivity {
    private static final String TAG = "DownloadManagerActivity";

    private DownloadManager downloadManager;
    private String url = "https://d1.music.126.net/dmusic/NeteaseCloudMusic_Music_official_7.1.42.1587721536.apk";
    private long downloadId;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                query();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri source = Uri.parse(url);
        File file = new File(Environment.getExternalStorageDirectory(), "download/test_download");
        Uri dest = Uri.fromFile(file);
        DownloadManager.Request request = new DownloadManager.Request(source);
        //在通知栏中显示下载
        request.setNotificationVisibility(View.VISIBLE);
//        request.setTitle()
//        request.setDescription()
        request.setVisibleInDownloadsUi(false);
        request.setDestinationUri(dest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresDeviceIdle(false);
            request.setRequiresCharging(false);
        }
        //开始下载
        findViewById(R.id.btn).setOnClickListener(v -> {
            downloadId = downloadManager.enqueue(request);
            query();
        });
        //监听进度
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            switch (action) {
                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                    Log.i(TAG, "onReceive: ACTION_DOWNLOAD_COMPLETE");
                    break;
                case DownloadManager.ACTION_NOTIFICATION_CLICKED:
                    Log.i(TAG, "onReceive: ACTION_NOTIFICATION_CLICKED");
                    break;
                case DownloadManager.ACTION_VIEW_DOWNLOADS:
                    Log.i(TAG, "onReceive: ACTION_VIEW_DOWNLOADS");
                    break;
            }
        }
    };

    private DownloadManager.Query query;
    private Cursor cursor;

    private void query() {
        if (query == null) {
            query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            cursor = downloadManager.query(query);
        }
        if (cursor != null && cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i(TAG, "onReceive: STATUS_PAUSED");
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.i(TAG, "onReceive: STATUS_FAILED");
                    cursor.close();
                    unregisterReceiver(receiver);
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.i(TAG, "onReceive: STATUS_SUCCESSFUL");
                    cursor.close();
                    unregisterReceiver(receiver);
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Log.i(TAG, "onReceive: STATUS_RUNNING");
                    if (query == null) {
                        query = new DownloadManager.Query();
                        query.setFilterById(downloadId);
                    }
                    int fileName = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                    int fileUri = cursor.getColumnIndex(DownloadManager.COLUMN_URI);
                    String fn = cursor.getString(fileName);
                    String fu = cursor.getString(fileUri);

                    int totalSizeBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                    int bytesDownloadSoFarIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                    // 下载的文件总大小
                    int totalSizeBytes = cursor.getInt(totalSizeBytesIndex);
                    // 截止目前已经下载的文件总大小
                    int bytesDownloadSoFar = cursor.getInt(bytesDownloadSoFarIndex);
                    Log.i(TAG, "from " + fu + " 下载到本地 " + fn + " 文件总大小:" + totalSizeBytes + " 已经下载:" + bytesDownloadSoFar);

                    cursor.close();
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
                case DownloadManager.STATUS_PENDING:
                    Log.i(TAG, "onReceive: STATUS_PENDING");
                    break;
            }
        }
        handler.sendEmptyMessageDelayed(1, 2000);
    }

}
