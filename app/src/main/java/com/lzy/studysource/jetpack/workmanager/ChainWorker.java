package com.lzy.studysource.jetpack.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author: cyli8
 * @date: 2019-10-15 19:37
 */
public class ChainWorker extends Worker {

    public ChainWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(WorkManagerActivity.TAG, "doWork ChainWorker: " + System.currentTimeMillis());
        return Result.success();
    }
}
