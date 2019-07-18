package com.lzy.studysource.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;

public class SchedulerAcitvity extends AppCompatActivity {

    private static final String TAG = "SchedulerAcitvity";

    public static final String MESSENGER_INTENT_KEY = TAG + ".MESSENGER_INTENT_KEY";
    public static final String WORK_DURATION_KEY = TAG + ".WORK_DURATION_KEY";
    public static final int MSG_JOB_START = 0;
    public static final int MSG_JOB_STOP = 1;

    private int mJobId = 0;// 执行的JobId

    ComponentName mServieComponent;// 这就是我们的jobservice组件了

    private Button mBtn_StartJob;// 点击开始任务的按钮
    private Button mBtn_StopAllJob;// 点击结束所有任务的按钮
    private TextView mStateTv;//展示状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_acitvity);

        mServieComponent = new ComponentName(this, MyJobService.class);// 获取到我们自己的jobservice，同时启动该service
        mBtn_StartJob = findViewById(R.id.btn_start);
        mBtn_StopAllJob = findViewById(R.id.btn_clear);
        mStateTv = findViewById(R.id.tv);
        mBtn_StartJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });

        mBtn_StopAllJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAllJobs();
            }
        });
    }

    public void scheduleJob() {
        //开始配置JobInfo
        JobInfo.Builder builder = new JobInfo.Builder(mJobId++, mServieComponent);

        //设置任务的延迟执行时间(单位是毫秒)
        builder.setMinimumLatency(1000);
        //设置任务最晚的延迟时间。如果到了规定的时间时其他条件还未满足，你的任务也会被启动。
        builder.setOverrideDeadline(2000);

        //让你这个任务只有在满足指定的网络条件时才会被执行
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        //你的任务只有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务。
        builder.setRequiresDeviceIdle(true);
        //告诉你的应用，只有当设备在充电时这个任务才会被执行。
        builder.setRequiresCharging(true);

        // Extras, work duration.
        PersistableBundle extras = new PersistableBundle();
        extras.putLong(WORK_DURATION_KEY, 1000);

        builder.setExtras(extras);

        // Schedule job
        Log.d(TAG, "Scheduling job");
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // 这里就将开始在service里边处理我们配置好的job
        int scheduleId = mJobScheduler.schedule(builder.build());
        Log.d(TAG, "执行任务的id：" + scheduleId);

        //mJobScheduler.schedule(builder.build())会返回一个int类型的数据
        //如果schedule方法失败了，它会返回一个小于0的错误码。否则它会返回我们在JobInfo.Builder中定义的标识id。
    }

    // 当用户点击取消所有时执行
    public void cancelAllJobs() {
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.cancelAll();
        Toast.makeText(this, "已清除所有任务", Toast.LENGTH_SHORT).show();
    }

}
