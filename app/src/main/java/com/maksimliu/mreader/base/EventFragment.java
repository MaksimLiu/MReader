package com.maksimliu.mreader.base;


import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class EventFragment extends BaseFragment {


    public EventFragment() {
        // Required empty public constructor
    }
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
