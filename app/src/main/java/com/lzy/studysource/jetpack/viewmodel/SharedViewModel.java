package com.lzy.studysource.jetpack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * created by 李朝阳 on 2020/6/2 15:23
 */
public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> selected = new MutableLiveData<>();

    public void setSelected(String sel) {
        selected.setValue(sel);
    }

    public LiveData<String> getSelected() {
        return selected;
    }

}
