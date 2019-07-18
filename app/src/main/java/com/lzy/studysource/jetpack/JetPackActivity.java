package com.lzy.studysource.jetpack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackFragment;
import com.lzy.studysource.jetpack.ui.jetpack.JetPackPresenter;

public class JetPackActivity extends AppCompatActivity {
    private JetPackPresenter mPresenter;

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
    }
}
