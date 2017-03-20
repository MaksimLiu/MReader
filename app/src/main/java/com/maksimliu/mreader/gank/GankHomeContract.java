package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.db.model.GankHomeModel;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public interface GankHomeContract {

    String ANDROID_CATEGORY = "Android";
    String IOS_CATEGORY = "iOS";
    String OTHERS_CATEGORY = "瞎推荐";
    String FRONT_END_CATEGORY = "前端";
    String FULI_CATEGORY = "福利";
    String EXTRA_RESOURCE = "拓展资源";

    int NO_HOME_CACHE = 1;

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {

        void loadLocalData();


        void fetchGankDaily(String year, String monthOfYear, String dayOfMonth);


    }
}
