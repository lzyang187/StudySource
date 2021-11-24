package com.lzy.studysource.arithmetic;

/**
 * 位运算相关的算法
 *
 * @author: cyli8
 * @date: 2018/7/24 10:47
 */
public class Bit {
    /**
     * 判断整数是否是奇数
     * 一个数是偶数那么最后一个一定是 0 如果一个数是奇数那么最后一位一定是 1；
     * 而十进制 1 在 8 位二进制中表示为 0000 0001，我们只需将一个数个 1相与（&） 得到的结果如果是 1 则表示该数为奇数，否知为偶数
     */
    public static boolean isOdd(int num) {
        return (1 & num) == 1;
    }

    /**
     * 判断该整数是不是2的整数次幂
     * 一个整数如果是2的整数次幂，那么他用二进制表示完肯定有唯一一位为1其余各位都为 0，形如 0..0100…0。比如 8 是 2的3次幂，那么这个数表示为二进制位 0000 1000 。
     * 除此之外我们还应该想到，一个二进制如果表示为 0..0100…0，那么它减去1得到的数二进制表示肯定是 0..0011..1 的形式。那么这个数与自自己减一后的数相与得到结果肯定为0
     */
    public static boolean log2(int num) {
        return (num & (num - 1)) == 0;
    }

    /**
     * 几种状态的检查
     */
    public static void checkStatus() {
        int state1 = 0b001;
        int state2 = 0b010;
        int state3 = 0b100;
        int result = 0b000;
        result = result | state1;
        System.out.println("state1完成了 = " + ((result & state1) == state1));
        System.out.println("state2完成了 = " + ((result & state2) == state2));
        result = result | state2;
        System.out.println("state2完成了 = " + ((result & state2) == state2));
        result = result & (~state2);
        System.out.println("state2完成了 = " + ((result & state2) == state2));
        result = result | state2;
        result = result | state3;
        System.out.println("都完成了 = " +
                ((result & state1) == state1 &&
                        (result & state2) == state2 &&
                        (result & state3) == state3));

    }

}
