package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.bean.ZhiHuNewsBean;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public interface ZhiHuHomeContract {

    int SET_OLD_NEWS = 1; //选定某个日期时，将整个列表显示为该天的新闻
    int ADD_OLD_NEWS = 2; //上拉加载更多，添加过往新闻


    interface View extends BaseView<Presenter> {

        void bindData(ZhiHuNewsBean bean);

        void bindNewsData(ZhiHuNewsBean bean);

    }


    interface Presenter extends BasePresenter {
        void getLatestNews();

        void getOldNews(String date, int type);

    }
}

