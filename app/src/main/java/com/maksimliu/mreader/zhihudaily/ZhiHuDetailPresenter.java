package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.bean.ZhiHuDetailBean;
import com.maksimliu.mreader.network.GankHttpRequest;
import com.maksimliu.mreader.network.ZhihuHttpRequest;
import com.maksimliu.mreader.rx.BaseZhiHuObserver;
import com.maksimliu.mreader.rx.RxSchedulers;

/**
 * Created by MaksimLiu on 2017/3/19.
 */

public class ZhiHuDetailPresenter implements ZhiHuDetailContract.Presenter {


    private ZhiHuDetailContract.View view;

    private ZhiHuDailyApi zhiHuDailyApi;

    private RxSchedulers rxSchedulers;


    public ZhiHuDetailPresenter(ZhiHuDetailContract.View view) {

        this.view = view;

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
    public void getNewsDetail(String id) {

        zhiHuDailyApi.getNewsDetail(id)
                .compose(rxSchedulers.<ZhiHuDetailBean>transformer())
                .subscribe(new BaseZhiHuObserver<ZhiHuDetailBean>() {
                    @Override
                    protected void onSuccess(ZhiHuDetailBean zhiHuDetailBean) {
                        view.bindData(zhiHuDetailBean);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                        view.showError(errorMsg);
                    }
                });
    }

    @Override
    public String processHtmlContent(String body) {

        StringBuilder css = new StringBuilder();

        //添加引用本地CSS文件
        css.append("<link rel=\"stylesheet\" href=\"");
        css.append("file:///android_asset/zhihudaily_detail.css");
        css.append("\" type=\"text/css\" />");


        //补充完整HTML代码
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE HTML>\n")
                .append("<html>\n<head>\n <meta charset=\"utf-8\" />\n")
                .append(css.toString())
                .append("\n</head>\n<body")
                .append(body)
                .append("</body>\n<html>");

        return html.toString();
    }

}
