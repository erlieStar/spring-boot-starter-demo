package com.javashitang.teststarter.controller;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
public class Request {

    private String code;

    private String name;

    public Request(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
