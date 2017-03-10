package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.bean.ZhiHuDailyDetailBean;
import com.maksimliu.mreader.bean.ZhiHuDailyNewsBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.CacheInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
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
                .cache(new Cache(cacheFile, 1024 * 1024 * 10)) //10M缓存空间
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


        final EventManager.ZhiHuDailyNews event = EventManager.ZhiHuDailyNews.GET_LATEST;

        Call<ZhiHuDailyNewsBean> latestCall = zhiHuDailyApi.getLatestNews();

        latestCall.enqueue(new Callback<ZhiHuDailyNewsBean>() {
            @Override
            public void onResponse(Call<ZhiHuDailyNewsBean> call, Response<ZhiHuDailyNewsBean> response) {

                event.setObject(response.body());
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFailure(Call<ZhiHuDailyNewsBean> call, Throwable t) {


                view.showError("网络无法连接");

            }
        });

    }

    @Override
    public void getOldNews(String date, int type) {

        final EventManager.ZhiHuDailyNews event;

        if (type == 1) {
            event = EventManager.ZhiHuDailyNews.ADD_OLD_NEWS;
        } else {
            event = EventManager.ZhiHuDailyNews.SET_OLD_NEWS;
        }

        Call<ZhiHuDailyNewsBean> latestCall = zhiHuDailyApi.getOldeNews(date);

        latestCall.enqueue(new Callback<ZhiHuDailyNewsBean>() {
            @Override
            public void onResponse(Call<ZhiHuDailyNewsBean> call, Response<ZhiHuDailyNewsBean> response) {


                event.setObject(response.body());
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFailure(Call<ZhiHuDailyNewsBean> call, Throwable t) {


                view.showError("网络无法连接");

            }
        });
    }

    @Override
    public void getNewsDetail(String id) {

        final EventManager.ZhiHuDailyNewsDetail event = EventManager.ZhiHuDailyNewsDetail.GET_DETAIL;

        Call<ZhiHuDailyDetailBean> latestCall = zhiHuDailyApi.getNewsDetail(id);

        latestCall.enqueue(new Callback<ZhiHuDailyDetailBean>() {
            @Override
            public void onResponse(Call<ZhiHuDailyDetailBean> call, Response<ZhiHuDailyDetailBean> response) {

                event.setObject(response.body());
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFailure(Call<ZhiHuDailyDetailBean> call, Throwable t) {


                view.showError("网络无法连接");

            }
        });
    }
}

