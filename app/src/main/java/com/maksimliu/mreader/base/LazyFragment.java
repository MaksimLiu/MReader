package com.maksimliu.mreader.base;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public abstract class LazyFragment extends BaseFragment {

    /**
     * 标志Fragment是否可见
     */
    protected boolean isVisible=false;
    /**
     * 标志Fragment是否已初始化View
     */
    protected boolean isPrepared=false;

    /**
     * 标志Fragment是否已加载数据
     */
    protected boolean isFirstLoad=true;


    /**
     * Fragment懒加载，避免ViewPager的缓存机制加载过多Fragment
     *
     * @param isVisibleToUser
     */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {



        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();

        }else {
            isVisible=false;
            onInvisible();
        }

        super.setUserVisibleHint(isVisibleToUser);

    }

    /**
     * 延迟加载数据
     */
    protected abstract void lazyLoadData();

    /**
     * Fragment可见时
     */
    protected void onVisible() {
        lazyLoadData();
    }

    /**
     * Fragment不可见
     */
    protected void onInvisible() {
    }
}
