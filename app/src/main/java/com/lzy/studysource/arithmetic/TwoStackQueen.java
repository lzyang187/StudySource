package com.lzy.studysource.arithmetic;

import java.util.Stack;

/**
 * 2个堆栈(后进先出)实现一个队列(先进先出)：
 * 1、无论如果 StackA（最开始add元素的那个栈） 要往 StackB 中压入元素，那么必须选择一次性全部压入。
 * 2、无论什么时候从队列中取元素，必须保证元素是从 StackB 中 pop 出的，也就是说，当 StackB 不为空的时候绝不能再次向 StackB 中压入元素。
 *
 * @author: cyli8
 * @date: 2018/7/23 14:08
 */
public class TwoStackQueen<E> {
    private Stack<E> mStackA;
    private Stack<E> mStackB;

    public TwoStackQueen() {
        mStackA = new Stack<>();
        mStackB = new Stack<>();
    }

    public void add(E e) {
        mStackA.push(e);
    }

    public E poll() {
        if (mStackA.isEmpty() && mStackB.isEmpty()) {
            return null;
        }
        if (mStackB.isEmpty()) {
            while (!mStackA.isEmpty()) {
                mStackB.add(mStackA.pop());
            }
        }
        return mStackB.pop();
    }

    public E peek() {
        if (mStackA.isEmpty() && mStackB.isEmpty()) {
            return null;
        }
        if (mStackB.isEmpty()) {
            while (!mStackA.isEmpty()) {
                mStackB.add(mStackA.pop());
            }
        }
        return mStackB.peek();
    }

}
