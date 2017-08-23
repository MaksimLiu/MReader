package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.bean.ZhiHuDetailBean;

/**
 * Created by MaksimLiu on 2017/3/19.
 */

public interface ZhiHuDetailContract {


    interface Presenter extends BasePresenter {

        void getNewsDetail(String id);

        String processHtmlContent(String body);


    }


    interface View extends BaseView<Presenter>{

        void showHTML(String html);

        void bindData(ZhiHuDetailBean bean);

    }
}
