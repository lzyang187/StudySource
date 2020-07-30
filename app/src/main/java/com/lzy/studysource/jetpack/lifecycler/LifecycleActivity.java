package com.lzy.studysource.jetpack.lifecycler;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lzy.studysource.R;
import com.lzy.studysource.jetpack.viewmodel.SharedViewModel;
import com.lzy.studysource.jetpack.viewmodel.SharedViewModelFactory;

public class LifecycleActivity extends AppCompatActivity {
    private static final String TAG = "LifecycleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        getLifecycle().addObserver(new MyLocationListener("activity"));
        getSupportFragmentManager().beginTransaction().add(R.id.ll, new LifecycleFragment()).commitAllowingStateLoss();

        SharedViewModel sharedViewModel = new ViewModelProvider(this, new SharedViewModelFactory()).get(SharedViewModel.class);
        sharedViewModel.getSelected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged: " + s);
            }
        });
    }

}