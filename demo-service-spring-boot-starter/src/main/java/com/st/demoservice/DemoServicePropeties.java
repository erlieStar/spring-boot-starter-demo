package com.st.demoservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: lilimin
 * @Date: 2019/7/21 16:37
 */
@ConfigurationProperties(prefix = "demo.service")
public class DemoServicePropeties {

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
