package com.duke.mvp.bean;

/**
 * 登录返回的bean
 */
public class LoginBean {

    private String userId;
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
