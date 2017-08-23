package com.maksimliu.mreader.rx;

import android.view.KeyEvent;
import android.view.View;

import com.maksimliu.mreader.rx.BaseGankObserver;
import com.maksimliu.mreader.views.custom.LoadingView;

/**
 * Created by MaksimLiu on 2017/8/21.
 */

public abstract class ProgressObserver<T> extends BaseGankObserver<T>{

    private LoadingView loadingView;


    public ProgressObserver(final LoadingView loadingView){

        this.loadingView=loadingView;

        //返回键取消显示加载
        loadingView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode==KeyEvent.KEYCODE_BACK){
                    loadingView.setVisibility(View.GONE);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onFinish() {

        if (loadingView!=null&&View.VISIBLE==loadingView.getVisibility()){
            loadingView.setVisibility(View.GONE);
        }

    }
}
