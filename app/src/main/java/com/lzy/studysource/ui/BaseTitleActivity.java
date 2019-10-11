package com.lzy.studysource.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.lzy.studysource.R;

public class BaseTitleActivity extends BaseActivity {
    public static final String KEY_ACTIVITY_TITLE = "key_activity_title";
    private Toolbar mToolbar;
    private String mTitle;
    private TextView mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.mipmap.lib_view_back_nor);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mTitleTv = findViewById(R.id.title_tv);
        setTitle(mTitle);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_base_title;
    }

    @Override
    protected void initArgument(Intent intent) {
        mTitle = intent.getStringExtra(KEY_ACTIVITY_TITLE);
    }

    private void setTitle(String title) {
        mTitleTv.setText(title);
    }

}
