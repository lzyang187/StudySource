package com.lzy.studysource.jetpack.workmanager;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.lzy.studysource.MyApplication;
import com.lzy.studysource.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkManagerActivity extends AppCompatActivity {
    public static final String TAG = "WorkManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);
        //1、创建任务执行的条件约束对象
        Constraints constraints = new Constraints.Builder()
//                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
//                .setRequiresStorageNotLow(true)
//                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //2、创建一个执行1次的任务
        Data data = new Data.Builder().putString("key1", "value1").build();
        OneTimeWorkRequest compressWorkRequest = new OneTimeWorkRequest.Builder(CompressWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.SECONDS)
                //补偿策略，定义了补偿延迟在接下来的几次重试中会如何增加
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS)
                //输入和输出的值以键-值对的形式存储在Data对象中。
                //Wroker类调用Worker.getInputData()来获取输入参数。
                .setInputData(data)
                //添加标签
                .addTag("tag")
                //设置输入合并类型
                //为了管理来自多个父任务的输入，WorkManager使用InputMerger进行输入合并。
//                .setInputMerger(ArrayCreatingInputMerger.class)
                .build();

        //创建重复执行的任务
//        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(CompressWorker.class, 10, TimeUnit.SECONDS).build();

        //3、进行调度
        WorkManager.getInstance(MyApplication.getInstance()).enqueue(compressWorkRequest);

        //4、观察任务的信息
        LiveData<List<WorkInfo>> liveData = WorkManager.getInstance(MyApplication.getInstance()).getWorkInfosByTagLiveData("tag");
        liveData.observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                WorkInfo info = workInfos.get(0);
                Data outputData = info.getOutputData();
                String value2 = outputData.getString("key2");
                Log.e(TAG, "onChanged: " + info.getState() + " value2：" + value2);
            }
        });

        //通过任务id观察
//        LiveData<WorkInfo> liveData = WorkManager.getInstance(MyApplication.getInstance()).getWorkInfoByIdLiveData(compressWorkRequest.getId());
//        liveData.observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(WorkInfo workInfo) {
//
//            }
//        });

        //5、终止任务
//        WorkManager.getInstance(MyApplication.getInstance()).cancelWorkById(compressWorkRequest.getId());

        //链接任务
//        WorkContinuation workContinuation = WorkManager.getInstance(MyApplication.getInstance()).beginWith(compressWorkRequest);
//        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(ChainWorker.class);
//        Operation enqueue = workContinuation
//                .then(builder.build())
//                .then(builder.build())
//                .enqueue();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止所有指定tag的任务
        WorkManager.getInstance(MyApplication.getInstance()).cancelAllWorkByTag("tag");
    }

}
