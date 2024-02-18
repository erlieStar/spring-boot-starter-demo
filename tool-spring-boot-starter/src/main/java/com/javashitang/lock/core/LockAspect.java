package com.javashitang.lock.core;

import com.javashitang.lock.annotation.Lock;
import com.javashitang.lock.exception.FrequentOperationException;
import com.javashitang.lock.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
@Aspect
@Order(0)
@Configuration
public class LockAspect {

    private final String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    @Pointcut("@annotation(com.javashitang.lock.annotation.Lock)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Lock lock = method.getAnnotation(Lock.class);
        Object[] args = joinPoint.getArgs();

        String[] keyFields = lock.keyFields();
        if (keyFields == null) {
            return joinPoint.proceed();
        }

        int expireTime = lock.expireTime();
        String tip = lock.tip();
        String keyPrefix = lock.keyPrefix();
        String lockKey = getLockKey(keyPrefix, keyFields, method, args);
        String lockValue = UUID.randomUUID().toString();
        if (RedisUtil.tryLock(lockKey, lockValue, expireTime)) {
            try {
                return joinPoint.proceed();
            } finally {
                RedisUtil.releaseLock(lockKey, lockValue);
            }
        }
        throw new FrequentOperationException(tip);
    }

    private String getLockKey(String keyPrefix, String[] keyFields, Method method, Object[] args) {
        Map<String, Object> keyValue = new HashMap<>();
        ParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = pnd.getParameterNames(method);
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                keyValue.put(parameterNames[i], "null");
            } else if (isOriginType(args[i].getClass())) {
                keyValue.put(parameterNames[i], args[i]);
            } else {
                Field[] fields = args[i].getClass().getFields();
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        String name = field.getName();
                        Object value = field.get(args[i]);
                        keyValue.put(parameterNames[i] + "." + name, String.valueOf(value));
                    } catch (Exception e) {

                    }
                }
            }
        }
        String key = Arrays.stream(keyFields).map(k -> {
            if (keyValue.get(k) == null) {
                throw new RuntimeException("parameter no found");
            }
            return String.valueOf(keyValue.get(k));
        }).collect(Collectors.joining("-"));

        return keyPrefix + ":" + key;
    }

    public boolean isOriginType(Class clazz) {
        return Arrays.stream(types).parallel().anyMatch(t -> t.equals(clazz.getName()));
    }
}
