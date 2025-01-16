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

    public MutableLiveData<List<Integer>> intListLiveData = new MutableLiveData<>();
    public List<Integer> intList = new ArrayList<>();

    public void doIntList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                intList.add(1);
                intListLiveData.postValue(intList);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intList.add(2);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intList.add(3);
                intListLiveData.postValue(intList);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                intList.set(0, 5);
                intListLiveData.postValue(intList);
            }
        }).start();
    }

    public MutableLiveData<Boolean> booLiveData = new MutableLiveData<>();

    public void doBoolLiveData() {
        booLiveData.postValue(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                booLiveData.postValue(false);
                booLiveData.postValue(false);
                booLiveData.postValue(true);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                booLiveData.postValue(false);
            }
        }).start();
    }
}
