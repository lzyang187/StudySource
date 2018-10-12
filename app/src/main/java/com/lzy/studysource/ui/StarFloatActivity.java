package com.lzy.studysource.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;

public class StarFloatActivity extends AppCompatActivity {
    private FloatView floatview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_float);
        floatview = (FloatView) findViewById(R.id.float_view);
        init();
    }

    private void init() {
        List<String> list = new ArrayList<>();
        list.add("邓玉");
        list.add("马良");
        list.add("柴意意");
        list.add("田绪会");
        list.add("徐路路");
        list.add("朱姗姗");
        list.add("徐展");
        list.add("丁元一");
        list.add("于晓龙");
        list.add("很长很长很长很长很长");
        floatview.setList(list);
        floatview.setOnItemClickListener(new FloatView.OnItemClickListener() {
            @Override
            public void itemClick(int position, String value) {
                Toast.makeText(StarFloatActivity.this, "当前是第" + position + "个，其值是" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
