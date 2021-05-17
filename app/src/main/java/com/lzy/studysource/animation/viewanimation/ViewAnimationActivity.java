package com.lzy.studysource.animation.viewanimation;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lzy.studysource.R;
import com.lzy.studysource.databinding.ActivityViewAnimBinding;

public class ViewAnimationActivity extends AppCompatActivity {
    private static final String TAG = "ViewAnimationActivity";
    private ActivityViewAnimBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_anim);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        animation.setInterpolator(new LinearInterpolator());
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG, "onAnimationRepeat: ");
            }
        });
        mBinding.transBtn.startAnimation(animation);
    }
}
