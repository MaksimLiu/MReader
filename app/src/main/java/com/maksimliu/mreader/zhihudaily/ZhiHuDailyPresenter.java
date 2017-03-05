package com.maksimliu.mreader.zhihudaily;

import android.telecom.StatusHints;

import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.bean.ZhiHuDailyLatest;
import com.maksimliu.mreader.db.model.ZhiHuDailyNews;
import com.maksimliu.mreader.network.CacheInterceptor;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class ZhiHuDailyPresenter implements ZhiHuDailyContract.Presenter {

    private ZhiHuDailyContract.View view;


    private ZhiHuDailyApi zhiHuDailyApi;

    public ZhiHuDailyPresenter(ZhiHuDailyContract.View view) {

        this.view = view;
        view.setPresenter(this);

        File cacheFile = new File(MReaderApplication.getContext().getExternalCacheDir(), "zhihu_daily");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(new Cache(cacheFile,1024*1024*10)) //10M缓存空间
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(ZhiHuDailyApi.BASE_API_4_URL)
                .build();

        zhiHuDailyApi = retrofit.create(ZhiHuDailyApi.class);


    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getLatestNews() {

        MLog.i("get");

        Call<ZhiHuDailyLatest> latestCall = zhiHuDailyApi.getLatestNews();

        latestCall.enqueue(new Callback<ZhiHuDailyLatest>() {
            @Override
            public void onResponse(Call<ZhiHuDailyLatest> call, Response<ZhiHuDailyLatest> response) {

                EventBus.getDefault().post(response.body());

            }

            @Override
            public void onFailure(Call<ZhiHuDailyLatest> call, Throwable t) {


                view.showError("网络无法连接");

            }
        });

    }

    @Override
    public void getOldNews(String date) {

        Call<ZhiHuDailyLatest> latestCall = zhiHuDailyApi.getOldeNews(date);

        latestCall.enqueue(new Callback<ZhiHuDailyLatest>() {
            @Override
            public void onResponse(Call<ZhiHuDailyLatest> call, Response<ZhiHuDailyLatest> response) {

                EventBus.getDefault().post(response.body());

            }

            @Override
            public void onFailure(Call<ZhiHuDailyLatest> call, Throwable t) {


                view.showError("网络无法连接");

            }
        });
    }

    @Override
    public void getNewsDetail(String id) {

    }
}
