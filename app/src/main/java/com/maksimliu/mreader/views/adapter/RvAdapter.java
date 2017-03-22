package com.maksimliu.mreader.views.adapter;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public interface RvAdapter {


    int TEXT_VIEW_TYPE = 1;
    int IMAGE_TEXT_VIEW_TYPE = 2;
    int IMAGE_VIEW_TYPE = 3;
    int FOOT_LOADER_VIEW_TYPE = 4;

    void setLoading(boolean flag);
}
