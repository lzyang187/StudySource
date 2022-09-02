package com.lzy.studysource.jetpack.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

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
        fragmentScopeViewModel1.getIntLiveData().setValue(1);
        fragmentScopeViewModel2.getIntLiveData().setValue(2);

        containerView1.setOnClickListener(v -> fragmentScopeViewModel1.getIntLiveData().setValue(11));

        containerView2.setOnClickListener(v -> fragmentScopeViewModel2.getIntLiveData().setValue(22));
    }
}