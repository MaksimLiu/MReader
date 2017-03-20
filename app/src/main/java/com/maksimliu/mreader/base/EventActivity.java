package com.maksimliu.mreader.base;

import android.os.Bundle;

import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public abstract class EventActivity extends BaseActivity{


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
