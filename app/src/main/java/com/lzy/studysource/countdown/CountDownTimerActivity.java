package com.lzy.studysource.countdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.lzy.studysource.R;
import com.lzy.studysource.databinding.ActivityCountDownTimerBinding;

public class CountDownTimerActivity extends AppCompatActivity {
    private static final String TAG = "CountDownTimerActivity";
    private ActivityCountDownTimerBinding mBinding;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_count_down_timer);
        mCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "onFinish: ");
            }
        };
        mBinding.btnStart.setOnClickListener(v -> mCountDownTimer.start());
        mBinding.btnCancel.setOnClickListener(v -> mCountDownTimer.cancel());
    }
}