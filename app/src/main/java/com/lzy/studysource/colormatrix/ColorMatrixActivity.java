package com.lzy.studysource.colormatrix;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;

public class ColorMatrixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration verticaldividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = getResources().getDrawable(R.drawable.devider_decoration);
        verticaldividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(verticaldividerItemDecoration);
        DividerItemDecoration hordividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        Drawable drawable1 = getResources().getDrawable(R.drawable.devider_decoration_w);
        hordividerItemDecoration.setDrawable(drawable1);
        recyclerView.addItemDecoration(hordividerItemDecoration);
        List<ColorFilter> list = new ArrayList<>();
        list.add(new ColorFilter("黑白", ColorFilter.colormatrix_heibai));
        list.add(new ColorFilter("怀旧", ColorFilter.colormatrix_huajiu));
        list.add(new ColorFilter("哥特", ColorFilter.colormatrix_gete));
        list.add(new ColorFilter("淡雅", ColorFilter.colormatrix_danya));
        list.add(new ColorFilter("蓝调", ColorFilter.colormatrix_landiao));
        list.add(new ColorFilter("光晕", ColorFilter.colormatrix_guangyun));
        list.add(new ColorFilter("梦幻", ColorFilter.colormatrix_menghuan));
        list.add(new ColorFilter("酒红", ColorFilter.colormatrix_jiuhong));
        list.add(new ColorFilter("胶片", ColorFilter.colormatrix_fanse));
        list.add(new ColorFilter("湖光掠影", ColorFilter.colormatrix_huguang));
        list.add(new ColorFilter("褐片", ColorFilter.colormatrix_hepian));
        list.add(new ColorFilter("复古", ColorFilter.colormatrix_fugu));
        list.add(new ColorFilter("泛黄", ColorFilter.colormatrix_huan_huang));
        list.add(new ColorFilter("传统", ColorFilter.colormatrix_chuan_tong));
        list.add(new ColorFilter("胶片2", ColorFilter.colormatrix_jiao_pian));
        list.add(new ColorFilter("锐色", ColorFilter.colormatrix_ruise));
        list.add(new ColorFilter("清宁", ColorFilter.colormatrix_qingning));
        list.add(new ColorFilter("浪漫", ColorFilter.colormatrix_langman));
        list.add(new ColorFilter("夜色", ColorFilter.colormatrix_yese));
        ColorFilterAdapter adapter = new ColorFilterAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }


}
