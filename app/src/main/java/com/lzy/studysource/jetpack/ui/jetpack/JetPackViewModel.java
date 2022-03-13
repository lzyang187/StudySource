package com.lzy.studysource.jetpack.ui.jetpack;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class JetPackViewModel extends ViewModel {
    //页面销毁保存数据
    private MutableLiveData<List<User>> users;

    public MutableLiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.name = "name" + i;
            list.add(user);
        }
        users.setValue(list);
    }

    //fragment之间共享数据
    private MutableLiveData<User> selected = new MutableLiveData<>();

    public void setSelected(User user) {
        selected.setValue(user);
    }

    public MutableLiveData<User> getSelected() {
        return selected;
    }

    @Inject
    public JetPackViewModel() {

    }

    @Inject
    JetPackRepository mRepository;

    public void load() {
        mRepository.load();
    }
}
