package com.javashitang.demoservice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lilimin
 * @Date: 2019/7/21 16:41
 */
@Configuration
@ConditionalOnClass(DemoService.class)
@EnableConfigurationProperties(DemoServicePropeties.class)
public class DemoServiceAutoConfiguration {

    @Bean
    public DemoService demoService(DemoServicePropeties propeties) {
        return new DemoService(propeties.getHost(), propeties.getPort());
    }
}
