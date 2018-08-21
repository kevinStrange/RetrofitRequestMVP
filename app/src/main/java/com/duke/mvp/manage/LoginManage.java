package com.duke.mvp.manage;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.duke.mvp.base.BaseApplication;
import com.duke.mvp.base.BaseManage;
import com.duke.mvp.bean.LoginBean;
import com.duke.mvp.exception.ApiException;
import com.duke.mvp.interfaces.ILoginListener;
import com.duke.mvp.observer.CommonObserver;
import com.duke.mvp.transformer.CommonTransformer;
import com.duke.mvp.util.Lg;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author : Duke
 * date   : 2018/8/21
 * explain   :登录管理类，主要负责逻辑处理
 * version: 1.0
 */
public class LoginManage extends BaseManage {

    public void login(@NonNull String username, @NonNull String pwd, @NonNull final ILoginListener<LoginBean>
            listener) {
        Lg.d("開始執行網絡請求");
        httpService.login(username, pwd)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonObserver<LoginBean>(BaseApplication.getContext()) {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull LoginBean loginBean) {
                        listener.successInfo(loginBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);

                        listener.failInfo(e.message);
                    }
                });
    }

    /**
     * 以json方式提交
     * @param username
     * @param pwd
     * @param listener
     * @return
     */
    public boolean loginToJsonCommit(@NonNull String username, @NonNull String pwd, @NonNull final ILoginListener<LoginBean>
            listener) {
        JSONObject root = new JSONObject();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("param3", "string");
            requestData.put("param4", "string2");
            root.put("param1", "111");
            root.put("param2", "222");
            root.put("data", requestData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
        httpService.login(requestBody)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonObserver<LoginBean>(BaseApplication.getContext()) {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull LoginBean loginBean) {
                        listener.successInfo(loginBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        listener.failInfo(e.message);
                    }
                });
        return true;
    }
}
