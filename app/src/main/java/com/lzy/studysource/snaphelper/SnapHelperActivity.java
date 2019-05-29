package com.lzy.studysource.snaphelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;

public class SnapHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_snap_helper);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        //使当前Item居中显示，常用场景是横向的RecyclerView, 类似ViewPager效果，但是又可以快速滑动
//        LinearSnapHelper helper = new LinearSnapHelper();
        //PagerSnapHelper 限制一次只能滑动一页，不能快速滑动
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("第" + i + "个item");
        }
        SnapHelperAdatper adatper = new SnapHelperAdatper(this, list);
        recyclerView.setAdapter(adatper);
    }

}
