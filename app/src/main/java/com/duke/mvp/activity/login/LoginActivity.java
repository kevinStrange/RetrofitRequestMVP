package com.duke.mvp.activity.login;

import android.view.View;


import com.duke.mvp.R;
import com.duke.mvp.base.BaseActivity;
import com.duke.mvp.bean.LoginBean;
import com.duke.mvp.interfaces.ILoginListener;
import com.duke.mvp.manage.LoginManage;

public class LoginActivity extends BaseActivity implements ILoginListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        LoginManage loginManage = new LoginManage();
        loginManage.login("123", "123", this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void successInfo(LoginBean loginBean) {

    }

    @Override
    public void failInfo(String result) {

    }
}
