package com.lzy.studysource.java;

/**
 * @author: cyli8
 * @date: 2018/6/25 10:35
 */
public class Test {
    public static void main(String[] args) {
//        final List<Bean> beanList = new ArrayList<>();
//        Bean bean4 = new Bean("444", 4);
//        Bean bean2 = new Bean("222", 2);
//        Bean bean1 = new Bean("111", 1);
//        Bean bean3 = new Bean("333", 3);
//        beanList.add(bean4);
//        beanList.add(bean2);
//        beanList.add(bean1);
//        beanList.add(bean3);
//        Collections.sort(beanList, new Comparator<Bean>() {
//            @Override
//            public int compare(Bean o1, Bean o2) {
//                System.out.println(o1.age + " " + o2.age);
//                //按age正序排列
//                return o1.age - o2.age;
//            }
//        });
//        System.out.println(beanList.toString());
//
//        String str = String.format("%s很好听%1$s", "小幸运", "江南");
//        System.out.println(str);

//        TwoStackQueen<Integer> twoStackQueen = new TwoStackQueen<>();
//        twoStackQueen.add(1);
//        twoStackQueen.add(2);
//        twoStackQueen.add(3);
//        System.out.println(twoStackQueen.poll());
//        System.out.println(twoStackQueen.poll());
//        System.out.println(twoStackQueen.poll());
//        twoStackQueen.add(4);
//        System.out.println(twoStackQueen.poll());

//        System.out.println(Bit.isOdd(1));
//        System.out.println(Bit.isOdd(1024));

//        System.out.println(Bit.log2(0));
//        System.out.println(Bit.log2(1024));
        System.out.println("\\");
        System.out.println("\"");


        Integer i1 = new Integer(127);
        Integer i2 = new Integer(127);
        System.out.println(i1 == i2);
        System.out.println(i1.equals(i2));
        System.out.println("-----------");

        Integer i3 = new Integer(128);
        Integer i4 = new Integer(128);
        System.out.println(i3 == i4);
        System.out.println(i3.equals(i4));
        System.out.println("-----------");

        Integer i5 = 128;
        Integer i6 = 128;
        System.out.println(i5 == i6);
        System.out.println(i5.equals(i6));
        System.out.println("-----------");

        Integer i7 = 127;
        Integer i8 = 127;
        System.out.println(i7 == i8);
        System.out.println(i7.equals(i8));


    }
}
