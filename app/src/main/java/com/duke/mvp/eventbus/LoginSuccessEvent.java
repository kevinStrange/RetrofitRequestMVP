package com.duke.mvp.eventbus;

import com.duke.mvp.bean.LoginBean;

/**
 * author : Duke
 * date   : 2018/8/22
 * explain   :
 * version: 1.0
 */
public class LoginSuccessEvent {

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    private LoginBean loginBean;

    public LoginSuccessEvent(LoginBean loginBean) {
        setLoginBean(loginBean);
    }


}
