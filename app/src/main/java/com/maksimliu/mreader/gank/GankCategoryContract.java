package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.db.model.GankCategoryModel;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public interface GankCategoryContract {


    int NO_CATEGORY_CACHE = 1;

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void loadCategory(String category);


        void fetchCategory(String category, String page);


    }
}
