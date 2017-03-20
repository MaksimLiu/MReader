package com.maksimliu.mreader;

import android.app.Application;
import android.content.Context;

/**
 * Created by MaksimLiu on 2017/3/3.
 */

public class MReaderApplication extends Application {

    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();


    }





    public static Context getContext() {
        return context;
    }


}
