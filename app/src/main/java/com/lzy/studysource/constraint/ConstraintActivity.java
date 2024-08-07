package com.lzy.studysource.constraint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import com.lzy.studysource.R;
import com.lzy.studysource.jetpack.livedata.LiveDataBus;
import com.lzy.studysource.ui.BaseActivity;
import com.lzy.studysource.ui.BaseFragment;
import com.lzy.studysource.ui.BaseTitleActivity;

import java.io.File;

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
                "\ngetDir:" + getDir("fileName", MODE_PRIVATE).getAbsolutePath() +
                "\ngetDatabasePath:" + getDatabasePath("db").getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File dataDir = getDataDir();
            Log.i(TAG, "getDataDir(): " + dataDir);
        }
        File db = getDatabasePath("db");
        File parentFile = db.getParentFile();
        Log.i(TAG, "db parentFile: " + parentFile);
        //外部存储
        Log.i(TAG, "getExternalStorageDirectory:" + Environment.getExternalStorageDirectory().getAbsolutePath() +
                "\n getExternalCacheDir" + getExternalCacheDir().getAbsolutePath() +
                "\n getExternalFilesDir" + getExternalFilesDir(""));

        Placeholder placeholder = findViewById(R.id.placeholder);
//        placeholder.setContentId(R.id.btn_center);
        findViewById(R.id.btn_center).setOnClickListener(v -> {
            LiveDataBus.get().with("key_test").postValue("hahaha");
            Intent intent = new Intent(this, BaseTitleActivity.class);
            intent.putExtra(BaseActivity.KEY_FRAGMENT_CLASS_NAME, BaseFragment.class.getName());
            intent.putExtra(BaseTitleActivity.KEY_ACTIVITY_TITLE, "页面的标题");
            startActivity(intent);
        });
    }
}
