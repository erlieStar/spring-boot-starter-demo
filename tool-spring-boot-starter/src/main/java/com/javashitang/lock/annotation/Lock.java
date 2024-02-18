package com.javashitang.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Lock {

    String[] keyFields();

    String keyPrefix() default "";

    String tip() default "操作频繁，请稍后再试";

    int expireTime() default 600;
}
