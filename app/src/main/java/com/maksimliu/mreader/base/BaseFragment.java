package com.maksimliu.mreader.base;



import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public abstract class BaseFragment extends Fragment {



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    protected abstract void setupView();
}
