package com.lzy.studysource.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lzy.studysource.R;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(mToolbar);

    }



}
