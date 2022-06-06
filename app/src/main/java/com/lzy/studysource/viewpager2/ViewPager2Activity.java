package com.lzy.studysource.viewpager2;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setUserInputEnabled(true);
        CheckBox userInput = findViewById(R.id.user_input_checkbox);
        userInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewPager2.setUserInputEnabled(isChecked);
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.horizontal) {
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                } else if (checkedId == R.id.vertical) {
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                }
            }
        });

//        List<ListFragment> listFragments = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            listFragments.add(new ListFragment());
//        }
//        viewPager2.setAdapter(new FragmentStateAdapter(this) {
//            @NonNull
//            @Override
//            public Fragment createFragment(int position) {
//                return listFragments.get(position);
//            }
//
//            @Override
//            public int getItemCount() {
//                return listFragments.size();
//            }
//        });


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add("条目：" + i);
        }
        ViewPager2Adapter viewAdapter = new ViewPager2Adapter(this, list);
        viewPager2.setAdapter(viewAdapter);

        viewPager2.setCurrentItem(1);

        viewPager2.registerOnPageChangeCallback(new OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ViewPager2Activity.this, "滑动到" + position, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add("添加的数据" + list.size());
                viewAdapter.notifyItemInserted(list.size() - 1);
            }
        });

        findViewById(R.id.delete_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(0);
                viewAdapter.notifyItemRemoved(0);
            }
        });

        viewPager2.setOffscreenPageLimit(3);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(list.get(position));
            }
        });
        tabLayoutMediator.attach();

    }


}

