package com.javashitang.teststarter.controller;

import com.javashitang.lock.annotation.Lock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
@RestController
public class TestController {

    @Lock(keyFields = {"order"})
    @RequestMapping("lock1")
    public String lock1(String order) {
        return "success";
    }

    @Lock(keyFields = {"request.code"})
    @RequestMapping("lock2")
    public String lock2(Request request) {
        return "success";
    }

    @Lock(keyFields = {"request.code", "request.name"})
    @RequestMapping("lock3")
    public String lock3(Request request) {
        return "success";
    }
}
