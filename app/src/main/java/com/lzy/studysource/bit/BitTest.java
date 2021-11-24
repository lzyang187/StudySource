package com.lzy.studysource.bit;

import com.lzy.studysource.arithmetic.Bit;

/**
 * @author: cyli8
 * @date: 2018/11/18 09:55
 */
public class BitTest {
    public static void main(String[] args) {
        System.out.println("1&0=" + (1 & 0));
        System.out.println("1|0=" + (1 | 0));
        System.out.println("~0=" + (~0));
        System.out.println("0017=" + 0017);
        System.out.println("0x01=" + 0x01);
        System.out.println("0x001=" + 0x001);
        System.out.println("0x00a=" + 0x00a);
        System.out.println("0b001=" + (short) 0b001);
        System.out.println("0b011=" + (short) 0b011);

        Bit.checkStatus();
    }
}
