package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.bean.ZhiHuNewsBean;
import com.maksimliu.mreader.network.GankHttpRequest;
import com.maksimliu.mreader.network.ZhihuHttpRequest;
import com.maksimliu.mreader.rx.BaseZhiHuObserver;
import com.maksimliu.mreader.rx.RxSchedulers;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class ZhiHuHomePresenter implements ZhiHuHomeContract.Presenter {

    private ZhiHuHomeContract.View mView;

    private ZhiHuDailyApi zhiHuDailyApi;

    private RxSchedulers rxSchedulers;

    public ZhiHuHomePresenter(ZhiHuHomeContract.View mView) {

        this.mView = mView;
        mView.setPresenter(this);
        zhiHuDailyApi = ZhihuHttpRequest.create(ZhiHuDailyApi.class);
        rxSchedulers = new RxSchedulers();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getLatestNews() {


        zhiHuDailyApi.getLatestNews()
                .compose(rxSchedulers.<ZhiHuNewsBean>transformer())
                .subscribe(new BaseZhiHuObserver<ZhiHuNewsBean>() {
                    @Override
                    protected void onSuccess(ZhiHuNewsBean bean) {
                        mView.bindData(bean);
                        mView.setIsLoading(false);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }
                });

    }


    @Override
    public void getOldNews(String date, final int type) {

        zhiHuDailyApi.getOldNews(date)
                .compose(rxSchedulers.<ZhiHuNewsBean>transformer())
                .subscribe(new BaseZhiHuObserver<ZhiHuNewsBean>() {
                    @Override
                    protected void onSuccess(ZhiHuNewsBean bean) {

                        if (type == ZhiHuHomeContract.SET_OLD_NEWS) {
                            mView.bindData(bean);
                        } else if (type == ZhiHuHomeContract.ADD_OLD_NEWS) {
                            mView.bindNewsData(bean);
                        }
                        mView.setIsLoading(false);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }
                });

    }

}

