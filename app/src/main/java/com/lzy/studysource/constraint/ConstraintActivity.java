package com.lzy.studysource.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import com.lzy.studysource.R;
import com.lzy.studysource.ui.BaseActivity;
import com.lzy.studysource.ui.BaseFragment;
import com.lzy.studysource.ui.BaseTitleActivity;

public class ConstraintActivity extends AppCompatActivity {
    private static final String TAG = "ConstraintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        //内部存储
        Log.i(TAG, "getDataDirectory:" + Environment.getDataDirectory().getAbsolutePath() +
                "\ngetFilesDir:" + getFilesDir().getAbsolutePath() +
                "\ngetCacheDir:" + getCacheDir().getAbsolutePath() +
                "\ngetDir:" + getDir("fileName", MODE_PRIVATE).getAbsolutePath());
        //外部存储
        Log.i(TAG, "getExternalStorageDirectory:" + Environment.getExternalStorageDirectory().getAbsolutePath() +
                "\n getExternalCacheDir" + getExternalCacheDir().getAbsolutePath());

        Placeholder placeholder = findViewById(R.id.placeholder);
//        placeholder.setContentId(R.id.btn_center);
        findViewById(R.id.btn_center).setOnClickListener(v -> {
            Intent intent = new Intent(this, BaseTitleActivity.class);
            intent.putExtra(BaseActivity.KEY_FRAGMENT_CLASS_NAME, BaseFragment.class.getName());
            intent.putExtra(BaseTitleActivity.KEY_ACTIVITY_TITLE, "页面的标题");
            startActivity(intent);
        });
    }
}
