package com.duke.mvp.base;

/**
 * author : Duke
 * date   : 2018/8/21
 * explain   :抽取的一个基类的bean, 直接在泛型中传data就行
 * version: 1.0
 */
public class BaseHttpResult<T> {
    private int status; // 状态码
    private String message; // 信息
    private T data; // 数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
