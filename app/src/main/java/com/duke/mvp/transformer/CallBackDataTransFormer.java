package com.duke.mvp.transformer;

import com.duke.mvp.base.BaseHttpResult;
import com.duke.mvp.exception.ErrorType;
import com.duke.mvp.exception.ExceptionEngine;
import com.duke.mvp.exception.ServerException;
import com.duke.mvp.util.Lg;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 封装返回数据Transformer
 *
 * @author huanghongfa
 * @since 1.0
 */
public class CallBackDataTransFormer<T> implements ObservableTransformer<BaseHttpResult<T>, T> {
    private static CallBackDataTransFormer errorTransformer = null;
    private static final String TAG = "CallBackDataTransFormer";

    @Override
    public ObservableSource<T> apply(@NonNull Observable<BaseHttpResult<T>> upstream) {
        return upstream.map(new Function<BaseHttpResult<T>, T>() {
            @Override
            public T apply(@NonNull BaseHttpResult<T> tBaseHttpResult) throws Exception {
                if (tBaseHttpResult == null) {
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                }
                Lg.e(TAG, tBaseHttpResult.toString());
                if (tBaseHttpResult.getStatus() != ErrorType.SUCCESS) {
                    throw new ServerException(tBaseHttpResult.getStatus(),
                            tBaseHttpResult.getMessage());
                }
                return tBaseHttpResult.getData();
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(@NonNull Throwable throwable)
                    throws Exception {
                // ExceptionEngine 为处理异常的驱动器 throwable
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });

    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> CallBackDataTransFormer<T> getInstance() {
        if (errorTransformer == null) {
            synchronized (CallBackDataTransFormer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new CallBackDataTransFormer<>();
                }
            }
        }
        return errorTransformer;
    }

}
