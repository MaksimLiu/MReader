package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;
import com.maksimliu.mreader.bean.GankBean;
import com.maksimliu.mreader.db.model.GankDailyModel;

import io.realm.RealmObject;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public class GankContract {

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {

        GankDailyModel loadLocalTodayData();
        GankDailyModel loadLocalRecentData();
        void getEveryDayGank(String year,String monthOfYear,String dayOfMonth);

    }
}
