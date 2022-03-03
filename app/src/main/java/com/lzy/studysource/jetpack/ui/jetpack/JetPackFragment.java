package com.lzy.studysource.jetpack.ui.jetpack;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lzy.studysource.R;

import java.util.List;

public class JetPackFragment extends Fragment {
    private static final String TAG = "cyli8";
    private JetPackViewModel mViewModel;

    public static JetPackFragment newInstance() {
        return new JetPackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jet_pack_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JetPackViewModel.class);
        mViewModel.getUsers().observe(requireActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "onChanged: " + users.get(0).name);
            }
        });

        mViewModel.getSelected().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Log.d(TAG, "onChanged: " + user.name);
            }
        });
    }

}
