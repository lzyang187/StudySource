package com.lzy.studysource.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.lzy.studysource.R;

public class BaseActivity extends AppCompatActivity {
    public static final String KEY_FRAGMENT_CLASS_NAME = "fragment_class_name";

    private BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        Intent intent = getIntent();
        initArgument(intent);
        mFragment = getFragment(intent);
        if (null == mFragment) {
            finish();
            return;
        }
        // 管理器
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mFragment, "fragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void initArgument(Intent intent) {

    }

    protected int getLayoutID() {
        return R.layout.activity_base;
    }

    protected BaseFragment getFragment(Intent intent) {
        String clsName = intent.getStringExtra(KEY_FRAGMENT_CLASS_NAME);
        if (null != clsName) {
            try {
                BaseFragment fragment = (BaseFragment) Class.forName(clsName).newInstance();
                fragment.setArguments(intent.getExtras());
                return fragment;
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
