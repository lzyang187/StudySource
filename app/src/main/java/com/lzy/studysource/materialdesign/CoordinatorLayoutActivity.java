package com.lzy.studysource.materialdesign;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lzy.studysource.R;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("这里是标题");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(v, "我是一条提示", Snackbar.LENGTH_SHORT);
            snackbar.setAction("我知道了", v1 -> Toast.makeText(CoordinatorLayoutActivity.this, "被点击了", Toast.LENGTH_SHORT).show());
            snackbar.show();
        });

    }
}
