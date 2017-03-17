package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.db.model.GankCategoryModel;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public interface GankCategoryContract  {


    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        GankCategoryModel getLocalTodayData(String category);

        GankCategoryModel getLocalRecentData(String category);

        void getGankCategoryDaily(String category,String page);



    }
}
