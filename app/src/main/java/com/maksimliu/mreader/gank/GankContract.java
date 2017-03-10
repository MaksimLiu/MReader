package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.base.BasePresenter;
import com.maksimliu.mreader.base.BaseView;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public class GankContract {

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {

        void getEveryDayGank(String year,String monthOfYear,String dayOfMonth);

    }
}
