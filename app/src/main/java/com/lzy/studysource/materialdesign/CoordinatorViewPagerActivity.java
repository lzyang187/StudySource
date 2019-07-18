package com.lzy.studysource.materialdesign;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_view_pager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("详情页标题");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.lib_view_back_nor);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ViewPager viewPager = findViewById(R.id.viewpager);
        List<ListFragment> listFragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listFragments.add(new ListFragment());
        }
        PagerAdapter adapter = new PagerAdapter(listFragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        List<ListFragment> listFragments;

        public PagerAdapter(List<ListFragment> listFragments, FragmentManager fm) {
            super(fm);
            this.listFragments = listFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "tab名";
        }
    }
}
