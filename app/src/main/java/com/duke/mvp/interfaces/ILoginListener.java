package com.duke.mvp.interfaces;

import com.duke.mvp.bean.LoginBean;

/**
 * 登录接口
 *
 * @param <T>
 */

public interface ILoginListener<T> {
    void successInfo(LoginBean loginBean);

    void failInfo(String result);
}
