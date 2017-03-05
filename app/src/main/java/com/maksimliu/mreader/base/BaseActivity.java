package com.maksimliu.mreader.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MaksimLiu on 2017/3/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        EventBus.getDefault().register(this);
        afterCreate(savedInstanceState);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected abstract int getLayoutId();


    /**
     * 键盘事件监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //当后退栈最后一个Fragment执行返回键时，结束Activity，避免空白页面
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return false;
            }
        }
        return true;
    }

}
