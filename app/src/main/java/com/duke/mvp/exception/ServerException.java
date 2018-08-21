package com.duke.mvp.exception;

/**
 * 服务器错误
 *
 * @author huanghongfa
 */
public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
