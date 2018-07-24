package com.lzy.studysource.arithmetic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 2个队列实现1个栈
 * 1、任何时候两个队列总有一个是空的。
 * 2、添加元素总是向非空队列中 add 元素。
 * 3、取出元素的时候总是将元素除队尾最后一个元素外，导入另一空队列中，最后一个元素出队。
 *
 * @author: cyli8
 * @date: 2018/7/23 15:01
 */
public class TwoQueenStack<E> {
    private Queue<E> mQueueA;
    private Queue<E> mQueueB;

    public TwoQueenStack() {
        mQueueA = new LinkedList<>();
        mQueueB = new LinkedList<>();
    }

    public E push(E e) {
        if (!mQueueA.isEmpty()) {
            mQueueA.add(e);
        } else if (!mQueueB.isEmpty()) {
            mQueueB.add(e);
        } else {
            mQueueA.add(e);
        }
        return e;
    }

    public E pop() {
        if (mQueueA.isEmpty() && mQueueB.isEmpty()) {
            return null;
        }
        E result = null;
        if (!mQueueA.isEmpty()) {
            while (!mQueueA.isEmpty()) {
                result = mQueueA.poll();
                if (!mQueueA.isEmpty()) {
                    mQueueB.add(result);
                }
            }
        } else {
            while (!mQueueB.isEmpty()) {
                result = mQueueB.poll();
                if (!mQueueB.isEmpty()) {
                    mQueueA.add(result);
                }
            }
        }
        return result;
    }

}
