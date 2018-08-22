package com.duke.mvp.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.duke.mvp.util.CrashHandler;
import com.zhy.autolayout.config.AutoLayoutConifg;

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
        AutoLayoutConifg.getInstance().useDeviceSize();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        mContext = this;
    }

    /**
     * @return 全局的上下文
     */
    public static Context getContext() {
        return mContext;
    }
}
