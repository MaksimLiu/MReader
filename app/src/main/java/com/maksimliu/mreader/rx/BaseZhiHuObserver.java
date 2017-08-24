package com.maksimliu.mreader.rx;


import com.maksimliu.lib.utils.ExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by MaksimLiu on 2017/8/23.
 */

public abstract class BaseZhiHuObserver<T> implements Observer<T> {

    protected Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {

        this.disposable = disposable;
        DisposableManager.add(disposable);

    }

    @Override
    public void onNext(@NonNull T t) {
        if (t == null) {

            onError("数据获取失败");
        } else {

            onSuccess(t);
        }

    }

    protected abstract void onSuccess(T t);

    @Override
    public void onError(@NonNull Throwable throwable) {

        String errorMsg = ExceptionUtil.getNetworkExceptionMessage(throwable);
        onError(errorMsg);
        onFinish();
    }

    protected abstract void onError(String errorMsg);

    protected void onFinish() {

    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
