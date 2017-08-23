package com.maksimliu.mreader.base;

import android.os.Bundle;
import android.view.View;

import com.maksimliu.mreader.rx.DisposableManager;
import com.maksimliu.mreader.views.custom.LoadingView;

/**
 * Created by MaksimLiu on 2017/8/22.
 */

public abstract class BaseRxFragment extends BaseFragment {

    protected LoadingView loadingView;


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        loadingView = new LoadingView(mContext);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loadingView != null && loadingView.getVisibility() == View.VISIBLE) {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DisposableManager.dispose();
    }
}
