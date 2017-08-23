package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.bean.GankCategoryBean;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public interface GankCategoryContract {


    int NO_IOS_CACHE = 1;
    int NO_FULI_CACHE = 2;
    int NO_OTHERS_CACHE = 3;

    interface View extends BaseView<Presenter> {

        void bindData(List<GankCategoryBean> beanList);
    }

    interface Presenter extends BasePresenter {


        void fetchCategory(String category, int page);


    }
}
