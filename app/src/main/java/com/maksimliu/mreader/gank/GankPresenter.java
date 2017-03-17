package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.bean.GankBean;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.db.DbHelper;
import com.maksimliu.mreader.db.model.GankDailyModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.CacheInterceptor;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaksimLiu on 2017/3/9.
 */

public class GankPresenter implements GankContract.Presenter{


    private GankContract.View view;

    private GankApi gankApi;

    private DbHelper<GankDailyModel> dbHelper;


    public GankPresenter(GankContract.View view) {

        this.view = view;
        view.setPresenter(this);

        dbHelper = DbHelper.getInstance();

        File cacheFile = new File(MReaderApplication.getContext().getExternalCacheDir(), "gank");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(new Cache(cacheFile, 1024 * 1024 * 10)) //10M缓存空间
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(GankApi.BASE_API_URL)
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public GankDailyModel loadLocalTodayData() {


        MLog.i(DateUtil.getToday());

        return dbHelper.get(GankDailyModel.class, "publishedAt", DateUtil.getToday(), DbHelper.GET_FIRST_MODEL);


    }

    @Override
    public GankDailyModel loadLocalRecentData() {

        return dbHelper.get(GankDailyModel.class, "publishedAt", DateUtil.getToday(), DbHelper.GET_LAST_MODEL);
    }

    @Override
    public void getEveryDayGank(String year, String monthOfYear, String dayOfMonth) {
        final EventManager.Gank gankEvent = EventManager.Gank.GET_EVERY_DAY_GANK;

        Call<GankBean> gankCall = gankApi.getEveryDayGank(year, monthOfYear, dayOfMonth);

        gankCall.enqueue(new Callback<GankBean>() {
            @Override
            public void onResponse(Call<GankBean> call, Response<GankBean> response) {

                gankEvent.setObject(response.body());
                EventBus.getDefault().post(gankEvent);

            }

            @Override
            public void onFailure(Call<GankBean> call, Throwable t) {


                view.showError("加载失败");
            }
        });
    }

}

