package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public interface ZhiHuDailyContract {

    interface View extends BaseView<Presenter>{


        void showError(String msg);


    }


    interface Presenter extends BasePresenter{
        void getLatestNews();

        void getOldNews(String date);
        void getNewsDetail(String id);
    }
}

