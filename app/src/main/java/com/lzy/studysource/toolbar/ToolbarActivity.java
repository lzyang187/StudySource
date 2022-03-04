package com.lzy.studysource.toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lzy.studysource.R;

public class ToolbarActivity extends AppCompatActivity {
    private static final String TAG = "ToolbarActivity";

    private Toolbar toolbar;
    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        toolbar = findViewById(R.id.toolbar);
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
        toolbar.setTitle("标题很长很长很长的标题哈哈哈哈哈哈啊哈哈哈很长哈哈哈哈哈哈啊哈哈哈");
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


        Button btn1 = findViewById(R.id.compat_btn);
        // 使用悬浮上下文菜单。当用户长按（按住）某个声明支持上下文菜单的视图时，菜单会显示为菜单项的悬浮列表（类似对话框）
        // 注册应与上下文菜单关联的 View 并将其传递给 View
        registerForContextMenu(btn1);
        btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast("按钮长按了");
                return false;
            }
        });

        View btn2 = findViewById(R.id.btn);

        btn2.setOnClickListener(v -> {
            if (popupMenu == null) {
                // 弹出式菜单：PopupMenu 是锚定在 View 中的模态菜单。如果空间足够，它会显示在锚定视图下方，否则显示在其上方
                popupMenu = new PopupMenu(ToolbarActivity.this, btn2);
                popupMenu.getMenuInflater().inflate(R.menu.toolbar_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    switch (itemId) {
                        case R.id.action_search:
                            toast("搜索");
                            return true;
                        case R.id.action_notification:
                            toast("通知");
                            if (item.isChecked()) {
                                item.setChecked(false);
                            } else {
                                item.setChecked(true);
                            }
                            return true;
                        case R.id.item_1:
                            toast("item1");
                            return true;
                        case R.id.item_2:
                            toast("item2");
                            if (item.isChecked()) {
                                item.setChecked(false);
                            } else {
                                item.setChecked(true);
                            }
                            return true;
                    }
                    return false;
                });
                popupMenu.setOnDismissListener(menu -> toast("弹出式菜单消失了"));
            }
            popupMenu.show();
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                toast("搜索");
                return true;
            case R.id.action_notification:
                toast("通知");
                return true;
            case R.id.item_1:
                toast("item1");
                return true;
            case R.id.item_2:
                toast("item2");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        toolbar.getMenu().getItem(2).setTitle("item1重命名");
        switch (itemId) {
            case R.id.action_search:
                toast("搜索");
                return true;
            case R.id.action_notification:
                toast("通知");
                return true;
            case R.id.item_1:
                toast("item1");
                return true;
            case R.id.item_2:
                toast("item2");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 在运行时更改菜单项
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e(TAG, "onPrepareOptionsMenu: ");
        menu.getItem(3).setTitle("onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
