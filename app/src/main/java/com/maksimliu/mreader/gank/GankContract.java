package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.bean.GankBean;
import com.maksimliu.mreader.db.model.GankDailyModel;

import io.realm.RealmObject;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public interface GankContract {

     String ANDROID_CATEGORY = "Android";
     String IOS_CATEGORY = "iOS";
     String OTHERS_CATEGORY = "瞎推荐";
     String FRONT_END_CATEGORY = "前端";
     String FULI_CATEGORY = "福利";
    String EXTRA_RESOURCE="拓展资源";

    interface View extends BaseView<Presenter> {


    }

    interface Presenter<T> extends BasePresenter {

        GankDailyModel loadLocalTodayData();

        GankDailyModel loadLocalRecentData();

        void getEveryDayGank(String year, String monthOfYear, String dayOfMonth);



    }
}
