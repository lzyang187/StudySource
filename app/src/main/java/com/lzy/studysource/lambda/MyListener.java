package com.lzy.studysource.lambda;

/**
 * @author: cyli8
 * @date: 2018/9/16 20:16
 */
public interface MyListener {

    void add(int a, int b);

    default void fun1() {
        System.out.println("父类默认实现");
    }
}
