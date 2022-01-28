package com.lzy.studysource.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zyli44
 * @date: 2022/1/28 9:52
 * @description: Java通配符测试
 */
public class GenericTest {


    public static void main(String[] args) {
        List<Object> objectList = new ArrayList<>();
        objectList.add("test");
        Object o = objectList.get(0);
        System.out.println(o);

        /*
          通配符上限
         */
        List<? extends Number> extendsList = new ArrayList<>();
//        extendsList.add(1);
//        extendsList.add(1.0);

        /*
          通配符下限
         */
        List<? super Number> superList = new ArrayList<>();
        superList.add(1);
        superList.add(1.0);
        System.out.println(superList.get(0) instanceof Double);
        System.out.println(superList.get(1) instanceof Double);


        printClass(1);
        printClass(1.0);

        Num<Integer> integerNum = new Num<>();
        integerNum.print();
    }

    public static <T extends Number> void printClass(T t) {
        System.out.println(t.getClass());
    }
}


class Num<T extends Number> {
    public T t;

    public void print() {
        System.out.println(t);
    }
}
