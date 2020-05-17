package com.lzy.studysource.drawLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.lzy.studysource.R;

public class DrawLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);
        DrawerLayout drawerLayout = findViewById(R.id.draw_layout);
//        drawerLayout.openDrawer(Gravity.RIGHT, true);
        View drawContent = findViewById(R.id.draw_content);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(drawContent)) {
                    drawerLayout.closeDrawer(drawContent, true);
                } else {
                    drawerLayout.openDrawer(drawContent, true);
                }
            }
        });
    }
}
