package com.lzy.studysource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 * 1、Target：用来定义你的注解将应用于什么地方，如一个方法（METHOD）或一个域（FIELD）
 * 2、Retention：用来定义该注解在哪一个级别可用
 * （1）在源代码中（SOURCE）：注解将被编译器丢弃
 * （2）在类文件中（CLASS）：注解在class文件中可用，但会被VM丢弃
 * （3）在运行时（RUNTIME）：VM将在运行期也被保留注解，因此可用通过反射机制读取注解的信息
 * 例子是用来跟踪一个项目中的用例
 *
 * @author: cyli8
 * @date: 2022/1/9 16:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {

    int id();

    String description() default "no description";

}
