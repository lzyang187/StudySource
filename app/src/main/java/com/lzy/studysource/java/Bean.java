package com.lzy.studysource.java;

import java.io.Serializable;

/**
 * @author: cyli8
 * @date: 2018/6/25 10:34
 */
public class Bean implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;

    public String name;
    public int age;

    public Bean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    protected Bean clone() throws CloneNotSupportedException {
        return (Bean) super.clone();
    }

}
