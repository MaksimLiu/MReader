package com.maksimliu.mreader.rx;

import android.view.View;

import com.maksimliu.mreader.network.NetworkState;
import com.maksimliu.mreader.views.custom.LoadingView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaksimLiu on 2017/8/21.
 */

public class RxSchedulers {


    private static final int RETRY_TIME = 1;
    private boolean isShowLoading = true;
    private LoadingView loadingView;


    public RxSchedulers(LoadingView loadingView) {
        this.loadingView = loadingView;
    }

    public RxSchedulers() {
        this.isShowLoading = false;
    }


    public <T> ObservableTransformer<T, T> transformer() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.retry(RETRY_TIME)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (NetworkState.isConnected()) {
                                    if (isShowLoading) {
                                        if (loadingView != null && loadingView.getVisibility() == View.GONE) {
                                            loadingView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {

                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
