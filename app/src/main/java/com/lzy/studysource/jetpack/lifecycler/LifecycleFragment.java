package com.lzy.studysource.jetpack.lifecycler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.lzy.studysource.R;
import com.lzy.studysource.jetpack.viewmodel.SharedViewModel;
import com.lzy.studysource.jetpack.viewmodel.SharedViewModelFactory;

/**
 * created by 李朝阳 on 2020/6/2 14:21
 */
public class LifecycleFragment extends Fragment {

    private static final String TAG = "LifecycleFragment";

    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new MyLocationListener("fragment"));
        Lifecycle.State currentState = getLifecycle().getCurrentState();
        Log.i(TAG, "onCreate: " + currentState);
        boolean atLeast = getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED);
        Log.i(TAG, "onCreate: atLeast=" + atLeast);

        sharedViewModel = new ViewModelProvider(requireActivity(), new SharedViewModelFactory()).get(SharedViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frragment_lifecycle, null);
    }

    int i = 0;

    @Override
    public void onPause() {
        super.onPause();
        sharedViewModel.setSelected("哈哈哈哈" + (i++));
    }
}
