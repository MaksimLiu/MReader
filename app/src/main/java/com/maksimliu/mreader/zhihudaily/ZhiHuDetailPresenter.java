package com.maksimliu.mreader.zhihudaily;

import com.google.gson.Gson;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.entity.ZhiHuDetailBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.CacheInterceptor;
import com.maksimliu.mreader.utils.ACache;
import com.maksimliu.mreader.utils.MLog;

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
 * Created by MaksimLiu on 2017/3/19.
 */

public class ZhiHuDetailPresenter implements ZhiHuDetailContract.Presenter {


    private ZhiHuDetailContract.View view;

    private ACache aCache;

    private Gson gson;

    private ZhiHuDailyApi zhiHuDailyApi;

    public ZhiHuDetailPresenter(ZhiHuDetailContract.View view) {

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

        aCache = ACache.get(MReaderApplication.getContext());

        gson = new Gson();

        zhiHuDailyApi = retrofit.create(ZhiHuDailyApi.class);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void fetchNewsDetail(String id) {

        final EventManager.ZhiHuNewsDetail event = EventManager.ZhiHuNewsDetail.GET_DETAIL;

        Call<ZhiHuDetailBean> latestCall = zhiHuDailyApi.getNewsDetail(id);

        latestCall.enqueue(new Callback<ZhiHuDetailBean>() {
            @Override
            public void onResponse(Call<ZhiHuDetailBean> call, Response<ZhiHuDetailBean> response) {

                MLog.i(event.getClass().getSimpleName());
                event.setObject(response.body());
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFailure(Call<ZhiHuDetailBean> call, Throwable t) {


                view.showError("加载失败 " + t.getMessage());

            }
        });
    }

    @Override
    public void loadNewsDetail(String id) {
        MLog.i("loadNewsDetail");

        String detail = aCache.getAsString("zhihu_detail" + id);
        if (detail != null) {

            final EventManager.ZhiHuNewsDetail detailEvent = EventManager.ZhiHuNewsDetail.GET_DETAIL;

            MLog.i(detail);
            ZhiHuDetailBean detailBean = gson.fromJson(detail, ZhiHuDetailBean.class);

//          --  MLog.i(detailEvent.getClass().getSimpleName());
            detailEvent.setObject(detailBean);
            EventBus.getDefault().post(detailEvent);
        } else {

            MLog.i("Error");

            EventManager.ZhiHuNewsDetail errorEvent = EventManager.ZhiHuNewsDetail.ERROR;
            errorEvent.setObject(ZhiHuDetailContract.NO_DETAIL_CACHE);
            EventBus.getDefault().post(errorEvent);
        }
    }
}
