package com.lzy.studysource.jetpack;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lzy.studysource.R;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackFragment;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackPresenter;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JetPackActivity extends AppCompatActivity {
    private static final String TAG = "JetPackActivity";
    private JetPackPresenter mPresenter;

    private JetPackViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jet_pack_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, JetPackFragment.newInstance())
                    .commitNow();
        }
        mPresenter = new JetPackPresenter();
        getLifecycle().addObserver(mPresenter);

        mViewModel = new ViewModelProvider(this).get(JetPackViewModel.class);
        mViewModel.load();

        mViewModel.intListLiveData.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                Log.e(TAG, "intListLiveData value size: " + integers.size());
            }
        });
        mViewModel.doIntList();

        mViewModel.booLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e(TAG, "booLiveData: " + aBoolean);
            }
        });
        mViewModel.doBoolLiveData();
    }
}
