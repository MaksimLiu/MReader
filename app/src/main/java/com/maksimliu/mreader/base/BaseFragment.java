package com.maksimliu.mreader.base;



import android.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public abstract class BaseFragment extends Fragment {


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
