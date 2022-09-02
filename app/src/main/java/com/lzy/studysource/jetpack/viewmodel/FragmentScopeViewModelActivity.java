package com.lzy.studysource.jetpack.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.lzy.studysource.R;

public class FragmentScopeViewModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_scope_view_model);

        FragmentContainerView containerView1 = findViewById(R.id.container_1);
        FragmentContainerView containerView2 = findViewById(R.id.container_2);

        Fragment fragment1 = containerView1.getFragment();
        Fragment fragment2 = containerView2.getFragment();

        FragmentScopeViewModel fragmentScopeViewModel1 = new ViewModelProvider(fragment1).get(FragmentScopeViewModel.class);

        FragmentScopeViewModel fragmentScopeViewModel2 = new ViewModelProvider(fragment2).get(FragmentScopeViewModel.class);


        containerView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentScopeViewModel1.getIntLiveData().setValue(1);
            }
        });

        containerView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentScopeViewModel2.getIntLiveData().setValue(2);
            }
        });
    }
}