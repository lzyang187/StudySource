package com.lzy.studysource.annotation;

/**
 * @author: cyli8
 * @date: 2022/1/24 22:50
 */
class UseCaseClass {

//    @UseCase(id = 1, description = "ax")
//    private int mAge;

    @UseCase(id = 1, description = "方法1的注解描述")
    public void fun1() {
        System.out.println("fun1");
    }

}
