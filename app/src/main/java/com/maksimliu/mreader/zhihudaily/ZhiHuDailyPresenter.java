package com.maksimliu.mreader.zhihudaily;

import com.google.gson.Gson;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.bean.ZhiHuDailyDetailBean;
import com.maksimliu.mreader.bean.ZhiHuDailyNewsBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.CacheInterceptor;
import com.maksimliu.mreader.utils.ACache;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;
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

    private ACache aCache;

    private Gson gson;

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
    public void fetchLatestNews() {


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


                view.showError("加载失败");

            }
        });

    }

    @Override
    public void loadLatestNews() {

        EventManager.ZhiHuDailyNews event;
        MLog.i("loadLatestNews");

        String dailyNews = aCache.getAsString("zhihu_daily_news" + DateUtil.getToday());
        if (dailyNews != null) {
            MLog.i(dailyNews);
            event = EventManager.ZhiHuDailyNews.GET_LATEST;
            ZhiHuDailyNewsBean bean = gson.fromJson(dailyNews, ZhiHuDailyNewsBean.class);
            event.setObject(bean);
            EventBus.getDefault().post(event);

        } else {
            MLog.i("Error");
            event = EventManager.ZhiHuDailyNews.ERROR;
            event.setObject(ZhiHuDailyContract.NO_LATEST_NEWS_CACHE);
            EventBus.getDefault().post(event);
        }


    }

    @Override
    public void fetchOldNews(String date, int type) {

        final EventManager.ZhiHuDailyNews event;

        if (type == ZhiHuDailyContract.ADD_OLD_NEWS) {
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


                view.showError("加载失败");

            }
        });
    }

    @Override
    public void loadOldNews(Calendar calendar) {

        EventManager.ZhiHuDailyNews event;
        MLog.i("loadOldNews");

        String oldNews = aCache.getAsString("zhihu_daily_news" + DateUtil.getCurrentDate(calendar));
        if (oldNews != null) {
            MLog.i(DateUtil.getCurrentDate(calendar));
            MLog.i(oldNews);
            event = EventManager.ZhiHuDailyNews.ADD_OLD_NEWS;
            ZhiHuDailyNewsBean bean = gson.fromJson(oldNews, ZhiHuDailyNewsBean.class);
            event.setObject(bean);
            EventBus.getDefault().post(event);

        } else {
            MLog.i("Error");
            event = EventManager.ZhiHuDailyNews.ERROR;
            event.setObject(ZhiHuDailyContract.NO_OLD_NEWS_CACHE);
            EventBus.getDefault().post(event);
        }


    }


}

