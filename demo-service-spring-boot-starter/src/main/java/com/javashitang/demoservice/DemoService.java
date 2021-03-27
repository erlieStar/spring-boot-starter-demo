package com.javashitang.demoservice;

/**
 * @Author: lilimin
 * @Date: 2019/7/21 16:43
 */
public class DemoService {

    private String host;
    private int port;

    public DemoService() {

    }

    public DemoService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String sayHello() {
        return "hello, " + host + " " + port;
    }

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
