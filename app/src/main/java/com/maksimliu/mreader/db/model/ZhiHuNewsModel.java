package com.maksimliu.mreader.db.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuNewsModel  implements MultiItemEntity {


    private String date;

    private ZhiHuCommonNewsModel commonNewsModel;

    private ZhiHuTopNewsModel topNewsModel;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public ZhiHuCommonNewsModel getCommonNewsModel() {
        return commonNewsModel;
    }

    public void setCommonNewsModel(ZhiHuCommonNewsModel commonNewsModel) {
        this.commonNewsModel = commonNewsModel;
    }

    public ZhiHuTopNewsModel getTopNewsModel() {
        return topNewsModel;
    }

    public void setTopNewsModel(ZhiHuTopNewsModel topNewsModel) {
        this.topNewsModel = topNewsModel;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
