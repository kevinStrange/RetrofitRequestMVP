package com.duke.mvp.exception;

/**
 * HTTP错误
 *
 * @author huanghongfa
 */
public class ApiException extends RuntimeException {
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
