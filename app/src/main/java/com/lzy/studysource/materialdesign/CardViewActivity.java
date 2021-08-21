package com.lzy.studysource.materialdesign;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;

public class CardViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        // 动态改变视图高度
        findViewById(R.id.elevation_tv).setTranslationZ(30);

        // cliping，裁剪
        View clipView1 = findViewById(R.id.clipping_tv_1);
        clipView1.setClipToOutline(true);
        View clipView2 = findViewById(R.id.clipping_tv_2);
        clipView2.setClipToOutline(true);
        //获取 Outline
        ViewOutlineProvider provider1 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                // 修改outline为特定形状
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        };
        ViewOutlineProvider provider2 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                // 修改outline为特定形状
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };
        // 重新设置形状
        clipView1.setOutlineProvider(provider1);
        clipView2.setOutlineProvider(provider2);
    }
}
