package com.lzy.studysource.lambda;

/**
 * @author: cyli8
 * @date: 2018/9/16 21:12
 */
public class MyListenerImpl implements MyListener {
    @Override
    public void add(int a, int b) {

    }

    @Override
    public void fun1() {
        System.out.println("子类重写");
    }
}
