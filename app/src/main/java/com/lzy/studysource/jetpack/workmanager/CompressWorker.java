package com.lzy.studysource.jetpack.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author: cyli8
 * @date: 2019-10-15 16:58
 */
public class CompressWorker extends Worker {

    public CompressWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        uploadImg();
        //重复执行
//        return Result.retry();
        Data data = new Data.Builder().putString("key2", "value2").build();
        return Result.success(data);
    }

    private void uploadImg() {
        Data inputData = getInputData();
        String value1 = inputData.getString("key1");
        Log.e(WorkManagerActivity.TAG, "uploadImg: value1 = " + value1);
    }
}
