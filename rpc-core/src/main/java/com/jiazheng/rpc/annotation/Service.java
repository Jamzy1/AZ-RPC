package com.jiazheng.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个服务提供类，用于远程接口的实现类
 * @author Jamzy
 */
@Target(ElementType.TYPE)               //注解作用范围，只能作用在类上

//注解生命周期，source(只保留在源文件)<class(保留到class文件)<runtime(需要在运行时去动态获取注解信息)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    public String name() default "";

}