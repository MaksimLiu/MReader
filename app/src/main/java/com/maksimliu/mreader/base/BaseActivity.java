package com.maksimliu.mreader.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MaksimLiu on 2017/3/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initListener();
        checkForAppPermission();
        afterCreate(savedInstanceState);

    }

    protected abstract void initListener();

    protected abstract void initView();



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

            MLog.i("onKeyDown   "+getSupportFragmentManager().getBackStackEntryCount());
            //当后退栈最后一个Fragment执行返回键时，结束Activity，避免空白页面
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
                return false;
            }
        }
        return true;
    }

    /**
     * 检查App是否有所需权限
     * <p>
     * Android M(SDK>=22)
     * <p>
     * 1.读取和写入内置卡内容
     *
     * @return
     */
    public boolean checkForAppPermission() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestForAppPermission();
        }
        return false;

    }

    /**
     * 请求App所需权限
     * <p>
     * Android M(SDK>=22)
     */
    private void requestForAppPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConfig.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
    }


}
