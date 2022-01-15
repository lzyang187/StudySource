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

        Bean bean = new Bean("aa", 18);
        System.out.println(bean);
        try {
            Bean clone = bean.clone();
            System.out.println(clone);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        integerFun();
    }

    /**
     * 自动装箱的陷阱。包装类的"=="在不遇到算术运算的情况下不自动拆箱，以及equals()方法不处理数据转换的关系
     */
    public static void integerFun() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
    }
}
