package com.duke.mvp.activity.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.duke.mvp.R;
import com.duke.mvp.base.BaseActivity;
import com.duke.mvp.bean.LoginBean;
import com.duke.mvp.eventbus.LoginSuccessEvent;
import com.duke.mvp.interfaces.ILoginListener;
import com.duke.mvp.manage.LoginManage;
import com.duke.mvp.util.FileUtils;
import com.duke.mvp.util.Lg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements ILoginListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        FileUtils.init();
        verifyStoragePermissions(this);
//        Observable.timer(5, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
        LoginManage loginManage = new LoginManage();
        loginManage.login("123","123",mContext,this);
//                    }
//                });


    }

    /**
     * 动态申请权限
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onLoginSuccessEvent(LoginSuccessEvent event) {
        event.getLoginBean();
        Lg.d("onLoginSuccessEvent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void successInfo(LoginBean loginBean) {

    }

    @Override
    public void failInfo(String result) {

    }
}
