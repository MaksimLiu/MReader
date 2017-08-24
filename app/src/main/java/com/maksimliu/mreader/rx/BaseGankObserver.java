package com.maksimliu.mreader.rx;

import com.maksimliu.lib.utils.ExceptionUtil;
import com.maksimliu.mreader.bean.BaseGankBean;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by MaksimLiu on 2017/8/21.
 */

public abstract class BaseGankObserver<T> implements Observer<BaseGankBean<T>> {

    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {

        this.disposable = disposable;
        DisposableManager.add(disposable);
    }

    @Override
    public void onNext(@NonNull BaseGankBean<T> gankBean) {

        if (gankBean.isError() || gankBean.getResults().isEmpty()) {
            onError("数据获取失败");
        } else {
            List<T> data = gankBean.getResults();
            onSuccess(data);
        }

    }

    protected abstract void onSuccess(List<T> data);

    protected abstract void onError(String errorMsg);

    @Override
    public void onError(@NonNull Throwable e) {

        String errorMessage = ExceptionUtil.getNetworkExceptionMessage(e);
        onError(errorMessage);
        onFinish();
    }

    public void onFinish() {
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
