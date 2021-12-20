package com.lzy.studysource.statictest;

/**
 * @author: zyli44
 * @date: 2021/12/20 9:36
 * @description:
 */
public class StaticTest {
    public static void main(String[] args) {
        System.out.println("第一次创建实例-----");
        new Person();
        System.out.println("第二次创建实例-----");
        new Person();
    }
}

class Person {

    public int age;

    static {
        System.out.println("静态代码块1");
    }

    static {
        System.out.println("静态代码块2");
    }

    {
        System.out.println("非静态代码块1：" + age);
    }

    {
        show();
        System.out.println("非静态代码块2：" + age);
    }

    public Person() {
        System.out.println("构造函数调用");
    }

    public void show() {
        System.out.println("show method");
    }
}
