package com.maksimliu.mreader.gank.frontend;

import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.bean.BaseGankBean;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.gank.extras.GankExtrasContract;
import com.maksimliu.mreader.network.GankHttpRequest;
import com.maksimliu.mreader.rx.BaseGankObserver;
import com.maksimliu.mreader.rx.RxSchedulers;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/8/23.
 */

public class GankFrontEndPresenter implements GankFrontEndContract.Presenter {

    private GankApi gankApi;

    private RxSchedulers rxSchedulers;

    private GankFrontEndContract.View mView;

    public GankFrontEndPresenter(GankExtrasContract.View view) {
        this.mView = view;
        view.setPresenter(this);
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
