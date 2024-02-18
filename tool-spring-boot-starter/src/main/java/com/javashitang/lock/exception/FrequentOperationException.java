package com.javashitang.lock.exception;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
public class FrequentOperationException extends RuntimeException {

    public FrequentOperationException(String message) {
        super(message);
    }
}
