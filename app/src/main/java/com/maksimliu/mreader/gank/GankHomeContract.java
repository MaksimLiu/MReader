package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public interface GankHomeContract {


    String HOME="HOME";
    int NO_HOME_CACHE = 1;

    interface View extends BaseView<Presenter> {

        void showHTML(String body);


    }

    interface Presenter extends BasePresenter {

        void loadLocalData();

        String handleHTML(String html);

        void fetchGankDaily(String year, String monthOfYear, String dayOfMonth);


    }
}
