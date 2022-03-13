package com.lzy.studysource.jetpack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lzy.studysource.R;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackFragment;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackPresenter;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JetPackActivity extends AppCompatActivity {
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
    }
}
