package com.duke.mvp.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * author : Duke
 * date   : 2018/8/21 13:52
 * explain   : 父类Application，做一些初始化动作
 * version: 1.0
 */
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * @return 全局的上下文
     */
    public static Context getContext() {
        return mContext;
    }
}
