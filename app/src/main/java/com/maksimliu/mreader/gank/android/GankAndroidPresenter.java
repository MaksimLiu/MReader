package com.maksimliu.mreader.gank.android;

import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.bean.BaseGankBean;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.gank.GankCategoryContract;
import com.maksimliu.mreader.network.GankHttpRequest;
import com.maksimliu.mreader.rx.BaseGankObserver;
import com.maksimliu.mreader.rx.RxSchedulers;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/8/22.
 */

public class GankAndroidPresenter implements GankCategoryContract.Presenter {

    private GankAndroidContract.View mView;

    private GankApi gankApi;

    private RxSchedulers rxSchedulers;


    public GankAndroidPresenter(GankAndroidContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
        gankApi = GankHttpRequest.create(GankApi.class);
        rxSchedulers = new RxSchedulers();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void fetchCategory(String category, int page) {

        gankApi.getCategoryGank(category, page)
                .compose(rxSchedulers.<BaseGankBean<GankCategoryBean>>transformer())
                .subscribe(new BaseGankObserver<GankCategoryBean>() {
                    @Override
                    protected void onSuccess(List<GankCategoryBean> data) {
                        mView.bindData(data);
                        mView.setIsLoading(false);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }
                });

    }


}
