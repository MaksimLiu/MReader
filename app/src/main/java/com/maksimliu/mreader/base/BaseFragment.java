package com.maksimliu.mreader.base;



import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public abstract class BaseFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupView();
        initListener();
    }


    protected abstract void initListener();
    protected abstract void setupView();
}
