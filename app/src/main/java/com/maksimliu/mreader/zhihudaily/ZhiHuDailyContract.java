package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;

import java.util.Calendar;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public interface ZhiHuDailyContract {

    int SET_OLD_NEWS = 1; //选定某个日期时，将整个列表显示为该天的新闻
    int ADD_OLD_NEWS = 2; //上拉加载更多，添加过往新闻

    int NO_LATEST_NEWS_CACHE = 3;
    int NO_OLD_NEWS_CACHE = 4;

    int NO_DETAIL_CACHE = 5;


    interface View extends BaseView<Presenter> {


    }


    interface Presenter extends BasePresenter {
        void fetchLatestNews();

        void loadLatestNews();

        void fetchOldNews(String date, int type);

        void loadOldNews(Calendar calendar);


    }
}

