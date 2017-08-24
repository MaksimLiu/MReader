package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.bean.BaseGankBean;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.network.GankHttpRequest;
import com.maksimliu.mreader.rx.BaseGankObserver;
import com.maksimliu.mreader.rx.RxSchedulers;
import com.maksimliu.mreader.utils.MLog;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/8/24.
 */

public class GankCategoryPresenter implements GankCategoryContract.Presenter {


    private GankCategoryContract.View mView;

    private RxSchedulers rxSchedulers;

    private GankApi gankApi;


    public GankCategoryPresenter(GankCategoryContract.View view) {

        this.mView = view;
        mView.setPresenter(this);
        rxSchedulers = new RxSchedulers();
        gankApi = GankHttpRequest.create(GankApi.class);
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
                        MLog.i(mView.getClass().getSimpleName());
                    }

                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }
                });
    }
}
