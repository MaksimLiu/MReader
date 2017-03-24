package com.maksimliu.mreader;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by MaksimLiu on 2017/3/3.
 */

public class MReaderApplication extends Application {

    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initStetho();
        initUmeng();

    }

    private void initUmeng() {
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void initStetho() {

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }


    public static Context getContext() {
        return context;
    }


}
