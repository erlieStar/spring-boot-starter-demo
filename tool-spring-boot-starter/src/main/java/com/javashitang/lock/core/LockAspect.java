package com.javashitang.lock.core;

import com.javashitang.lock.annotation.Lock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
@Aspect
@Order(0)
@Component
public class LockAspect {

    @Pointcut("@annotation(com.javashitang.lock.annotation.Lock)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Lock lock = method.getAnnotation(Lock.class);

        String[] keyFields = lock.keyFields();
        if (keyFields == null) {
            return joinPoint.proceed();
        }

        int expireTime = lock.expireTime();
        String tip = lock.tip();
        String keyPrefix = lock.keyPrefix();
        return joinPoint.proceed();
    }
}
