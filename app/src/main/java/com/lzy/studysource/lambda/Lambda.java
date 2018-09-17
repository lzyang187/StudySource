package com.lzy.studysource.lambda;

import android.os.Build;
import android.view.View;

import com.lzy.studysource.java.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author: cyli8
 * @date: 2018/9/16 20:03
 */
public class Lambda {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        Runnable r1 = () -> {
            System.out.println("aa");
            System.out.println("aa");
        };

        Runnable r2 = () -> System.out.println("单行实现时更简洁写法");

        new Thread(r1).start();

        new Thread(() -> System.out.println("haha")).start();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        //带参数的写法
        View.OnClickListener l1 = (View view) -> System.out.println();
        View.OnClickListener l2 = (view) -> System.out.println();

        //自定义
        MyListener myListener = new MyListener() {
            @Override
            public void add(int a, int b) {

            }
        };

        MyListener myListener1 = (a, b) -> System.out.println(a + b);
        myListener1.fun1();

        MyListenerImpl impl = new MyListenerImpl();
        impl.fun1();

        List<Bean> list = new ArrayList<>();
        list.add(new Bean("1", 1));
        list.add(new Bean("2", 2));
        list.add(new Bean("3", 3));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.forEach(bean -> System.out.println(bean.age));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Stream<Bean> stream = list.stream();
            Stream<Bean> stream1 = stream.filter(bean -> bean.age > 2);
            stream1.forEach(bean -> System.out.println(bean.age));
        }

        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Stream<String> stringStream = stringList.stream().map(String::toUpperCase);
        }


    }
}
