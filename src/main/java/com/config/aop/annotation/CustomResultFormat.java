package com.config.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pangbohuan
 * @description 注解作用于自定义返回格式, 在方法上使用这个注解系统不会自动封装返回格式即可自定义返回格式,用在controller层
 * @date 2018-12-25 14:01
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CustomResultFormat {
    /**
     * value 后期扩展可写一些常量,value值只认可这些常量
     * 常量设置为定义好需要返回的数据格式,再用工厂模式或者是策略模式对不同的数据格式进行不同的封装
     */
    String value() default "";
}
