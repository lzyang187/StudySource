package com.lzy.studysource.toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lzy.studysource.R;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
//        toolbar.setLogo(R.mipmap.ic_drawer_home);
        toolbar.setNavigationIcon(R.mipmap.lib_view_back_nor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("点击导航");
            }
        });
//        toolbar.setTitle("标题");
//        toolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Title);
//        toolbar.setSubtitle("副标题");
//        toolbar.setSubtitleTextAppearance(this, R.style.Theme_ToolBar_Subtitle);
//        toolbar.inflateMenu(R.menu.toolbar_menu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int itemId = item.getItemId();
//                switch (itemId) {
//                    case R.id.action_search:
//                        toast("搜索");
//                        break;
//                    case R.id.action_notification:
//                        toast("通知");
//                        break;
//                    case R.id.item_1:
//                        toast("item1");
//                        break;
//                    case R.id.item_2:
//                        toast("item2");
//                        break;
//                }
//                return true;
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                toast("搜索");
                break;
            case R.id.action_notification:
                toast("通知");
                break;
            case R.id.item_1:
                toast("item1");
                break;
            case R.id.item_2:
                toast("item2");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
