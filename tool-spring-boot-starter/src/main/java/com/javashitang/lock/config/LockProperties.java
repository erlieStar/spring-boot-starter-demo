package com.javashitang.lock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
@ConfigurationProperties(prefix = LockProperties.PREFIX)
public class LockProperties {

    public static final String PREFIX = "spring.lock";

    private String address;

}
