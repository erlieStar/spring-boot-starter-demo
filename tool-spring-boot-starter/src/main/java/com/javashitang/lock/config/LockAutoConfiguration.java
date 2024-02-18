package com.javashitang.lock.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
public class LockAutoConfiguration {
}
