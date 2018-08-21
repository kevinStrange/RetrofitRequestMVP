package com.duke.mvp.base;


import com.duke.mvp.exception.ApiException;

import io.reactivex.Observer;

/**
 * 父类观察者
 *
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onError(Throwable e) {
        ApiException apiException = (ApiException) e;
        onError(apiException);
    }

    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);
}
