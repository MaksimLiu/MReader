package com.maksimliu.mreader.base;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public abstract class BaseFragment extends Fragment {


    protected Context mContext;

    private Unbinder mUnbinder;

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(getLayoutId(), null, false);

        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getPageName());
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getPageName());
    }

    private String getPageName() {
        return getClass().getSimpleName();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initVariable();
        initView(savedInstanceState);
        initListener();
    }
    

    protected abstract void initVariable();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected  void initListener(){}

    protected  void initView(Bundle savedInstanceState){}
}
