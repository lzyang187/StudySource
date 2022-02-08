package com.lzy.studysource.annotation;

import java.lang.reflect.Method;

/**
 * @author: zyli44
 * @date: 2022/2/8 16:28
 * @description: 运行时注解处理器，采用反射机制处理
 */
public class RuntimeAnnotationProcessor {

    public static void main(String[] args) {
        Method[] declaredMethods = UseCaseClass.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(UseCase.class)) {
                // 通过 getAnnotation 获取方法的注解
                UseCase useCase = declaredMethod.getAnnotation(UseCase.class);
                System.out.println("id = " + useCase.id() + " description = " + useCase.description());
            }
        }
    }
}
