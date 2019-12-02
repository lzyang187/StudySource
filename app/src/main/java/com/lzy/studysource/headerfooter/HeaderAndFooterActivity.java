package com.lzy.studysource.headerfooter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;
import com.lzy.studysource.headerfooter.wrapper.HeaderAndFooterRecyclerViewAdapter;
import com.lzy.studysource.headerfooter.wrapper.HeaderSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

public class HeaderAndFooterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_and_footer);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add("str：" + i);
        }
        HeaderAndFooterRecyclerViewAdapter adapter = new HeaderAndFooterRecyclerViewAdapter(new MyAdapter(this, list));
        gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup(adapter, 3));
        View header = View.inflate(this, R.layout.header_layout, null);
        header.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HeaderAndFooterActivity.this, "点击了头部", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.addHeaderView(header);
        adapter.addFooterView(header);
        recyclerView.setAdapter(adapter);

    }
}
