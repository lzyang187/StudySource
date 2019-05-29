package com.lzy.studysource.java;

/**
 * @author: cyli8
 * @date: 2018/10/22 09:00
 */
public class InnerClassTest {

    public static void main(String[] args) {
        InnerClassTest test = new InnerClassTest();
    }

    public int field1 = 1;
    int field2 = 2;
    private int field3 = 3;
    protected int field4 = 4;

    public InnerClassTest() {
        InnerClassA a = new InnerClassA();
        System.out.println(a.field5);
        System.out.println(a.field6);
        System.out.println(a.field7);
        System.out.println(a.field8);
    }

    public class InnerClassA {
        public int field5 = 5;
        int field6 = 6;
        private int field7 = 7;
        protected int field8 = 8;

        public InnerClassA() {
            System.out.println(field1);
            System.out.println(field2);
            System.out.println(field3);
            System.out.println(field4);
        }
    }
}
