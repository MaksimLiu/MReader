package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public interface GankCategoryContract {


    int NO_ANDROID_CACHE = 1;
    int NO_IOS_CACHE=2;
    int NO_FULI_CACHE=3;
    int NO_OTHERS_CACHE=4;
    int NO_FRONT_END_CACHE=5;
    int NO_EXTRA_RESOURCE=6;

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void loadCategory(String category);


        void fetchCategory(String category, String page);


    }
}
