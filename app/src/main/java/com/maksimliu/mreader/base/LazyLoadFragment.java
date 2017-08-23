package com.maksimliu.mreader.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public abstract class LazyLoadFragment extends BaseRxFragment {

    /**
     * 标志Fragment是否可见
     */
    protected boolean isVisible = false;
    /**
     * 标志Fragment是否已初始化View
     */
    protected boolean isPrepared = false;

    /**
     * 标志Fragment是否已加载过数据
     */
    protected boolean isLoaded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        isPrepared = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkLazyLoad();
    }

    /**
     * Fragment懒加载，避免ViewPager的缓存机制加载过多Fragment
     *
     * @param isVisibleToUser
     */


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        this.isVisible = isVisibleToUser;
        checkLazyLoad();
        super.setUserVisibleHint(isVisibleToUser);

    }

    private void checkLazyLoad() {

        if (!isPrepared) {
            return;
        }

        if (isVisible) {

            onVisible();

        } else {
            onInvisible();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        isLoaded = false;
        isVisible = false;
    }

    /**
     * 延迟加载数据
     */
    protected abstract void lazyLoadData();

    /**
     * Fragment可见时
     */
    protected void onVisible() {

        if (!isLoaded) {
            lazyLoadData();
            isLoaded = true;
        }
    }

    /**
     * Fragment不可见
     */
    protected void onInvisible() {
        if (isLoaded) {

            stopLazyLoad();
        }
    }

    public void stopLazyLoad() {

    }
}
