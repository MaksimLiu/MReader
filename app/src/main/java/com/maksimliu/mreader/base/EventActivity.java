package com.maksimliu.mreader.base;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public abstract class EventActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
