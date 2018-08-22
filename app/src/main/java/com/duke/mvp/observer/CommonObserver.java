package com.duke.mvp.observer;

import android.content.Context;

import com.duke.mvp.base.BaseObserver;
import com.duke.mvp.dialog.LoadingDialog;
import com.duke.mvp.exception.ApiException;
import com.duke.mvp.util.Lg;

import io.reactivex.disposables.Disposable;

/**
 * author : Duke
 * date   : 2018/8/21
 * explain   :封装Observer
 * version: 1.0
 */
public abstract class CommonObserver<T> extends BaseObserver<T> {
    private static final String TAG = "CommonObserver";
    private Context context;
    // Disposable 相当于RxJava1.x中的 Subscription，用于解除订阅 disposable.dispose();
    protected Disposable disposable;
    private boolean misShowDialog;

    /**
     * 在調用的時候就決定要不要彈加載框
     * @param context
     * @param isShowDialog
     */
    public CommonObserver(Context context,boolean isShowDialog) {
        this.context = context;
        misShowDialog = isShowDialog;
    }

    @Override
    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
        disposable = d;
        Lg.d("開始執行請求");
        if (misShowDialog){
            LoadingDialog.showLoading(context, "請稍等");
        }
    }

    @Override
    protected void onError(ApiException e) {
        Lg.e("HTTP错误 --> " + "code:" + e.code + ", message:" + e.message);
    }

    @Override
    public void onComplete() {
        Lg.d("請求返回了");
        if (misShowDialog){
            LoadingDialog.dismissLoading();
        }
    }
}
