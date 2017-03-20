package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;

/**
 * Created by MaksimLiu on 2017/3/19.
 */

public interface ZhiHuDetailContract {


    int NO_DETAIL_CACHE = 1;

    interface Presenter extends BasePresenter {

        void fetchNewsDetail(String id);

        void loadNewsDetail(String id);

    }


    interface View extends BaseView<Presenter> {


    }
}
