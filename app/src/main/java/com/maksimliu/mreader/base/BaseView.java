package com.maksimliu.mreader.base;

/**
 * Created by MaksimLiu on 2017/3/3.
 * <h3>View基类接口</h3>
 */

public interface BaseView<T> {


    void setPresenter(T presnter);

    void showError(String errorMsg);



}
